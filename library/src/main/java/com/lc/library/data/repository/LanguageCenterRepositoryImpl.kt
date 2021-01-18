package com.lc.library.data.repository

import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.server.models.model.AddChatGroupDetail
import com.lc.server.models.model.Community
import com.lc.server.models.request.*
import com.lc.server.models.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*

class LanguageCenterRepositoryImpl(
    private val dataSource: LanguageCenterDataSource,
    private val pref: PreferenceAuth,
) : LanguageCenterRepository {

    private suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                if (throwable is HttpException && (throwable.code() == 401 || throwable.code() == 403)) {
                    Resource.Error(throwable, true)
                } else {
                    Resource.Error(throwable, false)
                }
            }
        }

    override suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse> {
        val response = safeApiCall { dataSource.callSignIn(request) }

        if (response is Resource.Success) {
            saveTokenAuth(response.data)
            callFetchUserInfo()
        }

        return response
    }

    private fun saveTokenAuth(response: SignInResponse) {
        if (response.success) {
            pref.accessToken = response.token?.accessToken.orEmpty()
            pref.refreshToken = response.token?.refreshToken.orEmpty()
        }
    }

    override suspend fun callFetchUserInfo(): Resource<UserInfoResponse> {
        val response = safeApiCall { dataSource.callFetchUserInfo() }

        if (response is Resource.Success) saveUserInfoDb(response.data)

        return response
    }

    private suspend fun saveUserInfoDb(data: UserInfoResponse) {
        if (data.success) {
            val userInfo = data.userInfo
            val entity = UserInfoEntity(
                userId = userInfo?.userId.orEmpty(),
                email = userInfo?.email,
                givenName = userInfo?.givenName,
                familyName = userInfo?.familyName,
                name = userInfo?.name,
                picture = userInfo?.picture,
                gender = userInfo?.gender,
                birthDateString = userInfo?.birthDateString,
                birthDateLong = userInfo?.birthDateLong,
                verifiedEmail = userInfo?.verifiedEmail ?: false,
                aboutMe = userInfo?.aboutMe,
                created = userInfo?.created,
                updated = userInfo?.updated,
                localNatives = userInfo?.localNatives ?: emptyList(),
                localLearnings = userInfo?.localLearnings ?: emptyList(),
            )
            dataSource.saveUserInfo(entity)
        }
    }

    override suspend fun signOut() {
        pref.accessToken = ""
        pref.refreshToken = ""
        dataSource.deleteUserInfo()
        dataSource.deleteFriendInfo()
        dataSource.deleteAllAddChatGroupDetail()
        dataSource.deleteTalk()
    }

    override suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse> {
        val response = safeApiCall { dataSource.callGuideUpdateProfile(guideUpdateProfileRequest) }

        if (response is Resource.Success) callFetchUserInfo()

        return response
    }

    override suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse> {
        val response = safeApiCall { dataSource.callEditProfile(editProfileRequest) }

        if (response is Resource.Success) callFetchUserInfo()

        return response
    }

    override suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): Resource<BaseResponse> {
        val response = safeApiCall { dataSource.callEditLocale(editLocaleRequest) }

        if (response is Resource.Success) callFetchUserInfo()

        return response
    }

    override suspend fun callFetchFriendInfo(): Resource<FetchFriendInfoResponse> {
        val resource = safeApiCall { dataSource.callFetchFriendInfo() }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                val friendUserIdList = dataSource.getDbFriendInfoList()?.map { it.userId }

                var friendInfoList = resource.data.friendInfoList

                // filter
                val list = mutableListOf<Community>()
                friendUserIdList?.forEach { friendUserId ->
                    friendInfoList.filter { it.userId == friendUserId }
                        .onEach { list.add(it) }
                }
                friendInfoList = friendInfoList - list

                // save room database
                friendInfoList.forEach { friendInfo ->
                    val entity = FriendInfoEntity(
                        userId = friendInfo.userId.orEmpty(),
                        email = friendInfo.email,
                        givenName = friendInfo.givenName,
                        familyName = friendInfo.familyName,
                        name = friendInfo.name,
                        picture = friendInfo.picture,
                        gender = friendInfo.gender,
                        age = friendInfo.age,
                        birthDateString = friendInfo.birthDateString,
                        birthDateLong = friendInfo.birthDateLong,
                        aboutMe = friendInfo.aboutMe,
                        localNatives = friendInfo.localNatives,
                        localLearnings = friendInfo.localLearnings,
                    )
                    dataSource.saveFriendInfo(entity)
                }
            }
        }

        return resource
    }

    override suspend fun callFetchCommunity(): Resource<FetchCommunityResponse> {
        return safeApiCall { dataSource.callFetchCommunity() }
    }

    override suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddAlgorithm(addAlgorithmRequest) }
    }

    override suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): Resource<BaseResponse> {
        val talkId = UUID.randomUUID().toString().replace("-", "")
        val entity = TalkEntity(
            talkId = talkId,
            toUserId = sendMessageRequest.toUserId.orEmpty(),
            messages = sendMessageRequest.messages.orEmpty(),
        )
        dataSource.saveTalk(entity)

        val request = sendMessageRequest.copy(talkId = talkId)
        val resource = safeApiCall { dataSource.callSendMessage(request) }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                dataSource.updateIsSendMessage(talkId)
            }
        }

        return resource
    }

    override suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddChatGroup(addChatGroupRequest) }
    }

    override suspend fun callFetchChatGroup(): Resource<FetchChatGroupResponse> {
        return safeApiCall { dataSource.callFetchChatGroup() }
    }

    override suspend fun callFetchChatGroupDetail(chatGroupId: Int?): Resource<FetchChatGroupDetailResponse> {
        return safeApiCall { dataSource.callFetchChatGroupDetail(chatGroupId) }
    }

    override suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRenameChatGroup(renameChatGroupRequest) }
    }

    override suspend fun callRemoveChatGroup(chatGroupId: Int?): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRemoveChatGroup(chatGroupId) }
    }

    override suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse> {
        val resource = safeApiCall { dataSource.callFetchAddChatGroupDetail() }

        if (resource is Resource.Success) {
            var addChatGroupDetailList = resource.data.addChatGroupDetailList

            // filter
            val otherFriendUserId = dataSource.getDbAddChatGroupDetailList()?.map { it.userId }
            val list = mutableListOf<AddChatGroupDetail>()
            otherFriendUserId?.forEach { userId ->
                val element = addChatGroupDetailList.find { it.userId == userId }
                if (element != null) list.add(element)
            }
            addChatGroupDetailList = addChatGroupDetailList - list

            addChatGroupDetailList.forEach {
                val entity = AddChatGroupDetailEntity(
                    userId = it.userId.orEmpty(),
                    email = it.email,
                    givenName = it.givenName,
                    familyName = it.familyName,
                    name = it.name,
                    picture = it.picture,
                    gender = it.gender,
                    age = it.age,
                    birthDateString = it.birthDateString,
                    birthDateLong = it.birthDateLong,
                    aboutMe = it.aboutMe,
                    created = it.created,
                    localNatives = it.localNatives,
                    localLearnings = it.localLearnings,
                )
                dataSource.saveAddChatGroupDetail(entity)
            }
        }

        return resource
    }

    override suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeChatGroup(changeChatGroupRequest) }
    }

    override suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRemoveChatGroupDetail(removeChatGroupDetailRequest) }
    }

    override suspend fun callAddChatGroupFriend(
        chatGroupId: Int?,
        friendId: String?,
        friendInfoEntity: FriendInfoEntity
    ): Resource<BaseResponse> {
        val callApiResponse = if (chatGroupId == null) {
            val request = AddChatGroupNewRequest(friendId)
            safeApiCall { dataSource.callAddChatGroupNew(request) }
        } else {
            val request = AddChatGroupDetailRequest(
                chatGroupId = chatGroupId,
                userId = friendId,
            )
            safeApiCall { dataSource.callAddChatGroupDetail(request) }
        }

        if (callApiResponse is Resource.Success) {
            if (callApiResponse.data.success) {
                dataSource.saveFriendInfo(friendInfoEntity)

                dataSource.deleteAddChatGroupDetail(friendId)
            }
        }

        return callApiResponse
    }

}
