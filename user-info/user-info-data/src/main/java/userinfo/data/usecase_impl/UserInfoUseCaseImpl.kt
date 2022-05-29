package userinfo.data.usecase_impl

import android.util.Log
import core.kotlin.Result
import io.reactivex.Single
import userinfo.data.repository_impl.UserInfoRepositoryImpl
import userinfo.domain.model.UserInfoApiResponseData
import userinfo.domain.usecase.UserInfoUseCase
import javax.inject.Inject


class UserInfoUseCaseImpl @Inject constructor(
    private val userInfoRepository: UserInfoRepositoryImpl
): UserInfoUseCase {

    override fun getUserInfo(): Single<Result<UserInfoApiResponseData>> {
        return userInfoRepository.getUserInfo()
            .doOnSuccess {
                when(it) {
                    is Result.OnSuccess -> {
                        Log.e("Res",it.toString())
                    }

                    is Result.OnError -> {

                    }
                }
            }
    }

}