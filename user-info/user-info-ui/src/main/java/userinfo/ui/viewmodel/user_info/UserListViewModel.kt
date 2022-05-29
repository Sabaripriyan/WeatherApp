package userinfo.ui.viewmodel.user_info

import android.util.Log
import core.kotlin.Result
import core.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import userinfo.domain.model.UserInfoApiResponseData
import userinfo.domain.model.UserInfoData
import userinfo.domain.usecase.UserInfoUseCase
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
): BaseViewModel() {

    fun getUserInfoList() {
        disposable += userInfoUseCase.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                handleUserInfo(it)
            }
    }

    private fun handleUserInfo(it: Result<UserInfoApiResponseData>?) {
        Log.e("Res",it.toString())
    }
}