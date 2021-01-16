package com.lc.library.data.repository

import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.server.models.request.*
import com.lc.server.models.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

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
        dataSource.deleteAllAddChatGroupDetail()
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

    override suspend fun callFetchCommunity(): Resource<FetchCommunityResponse> {
        return safeApiCall { dataSource.callFetchCommunity() }
    }

    override suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddAlgorithm(addAlgorithmRequest) }
    }

    override suspend fun callAddChatGroupNew(addChatGroupNewRequest: AddChatGroupNewRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddChatGroupNew(addChatGroupNewRequest) }
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
        val fetchLastUserId = dataSource.getDbFetchLastUserId()
        val resource = safeApiCall { dataSource.callFetchAddChatGroupDetail(fetchLastUserId) }

        if (resource is Resource.Success) {
            val addChatGroupDetailList = resource.data.addChatGroupDetailList

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

    override suspend fun callAddChatGroupDetail(addChatGroupDetailRequest: AddChatGroupDetailRequest): Resource<BaseResponse> {
        val resource = safeApiCall { dataSource.callAddChatGroupDetail(addChatGroupDetailRequest) }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                dataSource.deleteAddChatGroupDetail(addChatGroupDetailRequest.userId)
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

}
