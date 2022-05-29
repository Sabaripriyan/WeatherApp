package userinfo.ui.viewmodel.user_info

import android.icu.lang.UCharacter
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import core.kotlin.Result
import core.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import userinfo.domain.model.UserInfoApiResponseData
import userinfo.domain.model.UserInfoData
import userinfo.domain.usecase.UserInfoUseCase
import userinfo.ui.view.adapter.UserListAdapter
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
): BaseViewModel() {

    lateinit var userListAdapter: UserListAdapter

    fun getUserInfoList() {
        disposable += userInfoUseCase.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                handleUserInfo(it)
            }
    }

    private fun handleUserInfo(it: Result<UserInfoApiResponseData>?) {
        when(it){
            is Result.OnSuccess -> {
                userListAdapter.updateList(it.data.results?: emptyList())
            }

            is Result.OnError -> {
            }
        }
    }


}