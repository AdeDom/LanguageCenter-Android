package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.server.models.request.*
import com.lc.server.models.response.*

interface LanguageCenterDataSource {

    suspend fun saveUserInfo(userInfo: UserInfoEntity)

    suspend fun getDbUserInfo(): UserInfoEntity?

    fun getDbUserInfoLiveData(): LiveData<UserInfoEntity>

    suspend fun deleteUserInfo()

    suspend fun saveAddChatGroupDetail(addChatGroupDetailEntity: AddChatGroupDetailEntity)

    suspend fun getDbAddChatGroupDetail(): AddChatGroupDetailEntity?

    fun getDbAddChatGroupDetailLiveData(): LiveData<AddChatGroupDetailEntity>

    suspend fun deleteAddChatGroupDetail()

    suspend fun callSignIn(request: SignInRequest): SignInResponse

    suspend fun callFetchUserInfo(): UserInfoResponse

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): BaseResponse

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): BaseResponse

    suspend fun callFetchCommunity(): FetchCommunityResponse

    suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): BaseResponse

    suspend fun callAddChatGroupNew(addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse

    suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): BaseResponse

    suspend fun callFetchChatGroup(): FetchChatGroupResponse

    suspend fun callFetchChatGroupDetail(chatGroupId: Int?): FetchChatGroupDetailResponse

    suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): BaseResponse

    suspend fun callRemoveChatGroup(chatGroupId: Int?): BaseResponse

    suspend fun callFetchAddChatGroupDetail(): FetchAddChatGroupDetailResponse

    suspend fun callAddChatGroupDetail(addChatGroupDetailRequest: AddChatGroupDetailRequest): BaseResponse

}
