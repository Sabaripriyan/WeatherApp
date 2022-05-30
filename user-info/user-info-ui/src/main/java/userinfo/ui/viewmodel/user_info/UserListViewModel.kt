package userinfo.ui.viewmodel.user_info

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import core.kotlin.Result
import core.model.ToolbarData
import core.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import userinfo.domain.model.user_info.UserInfoApiResponseData
import userinfo.domain.model.weather.CurrentWeatherData
import userinfo.domain.usecase.UserInfoUseCase
import userinfo.ui.view.adapter.UserListAdapter
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
): BaseViewModel() {

    lateinit var userListAdapter: UserListAdapter
    val toolbarData = MutableLiveData<ToolbarData>()

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

    fun getCurrentWeatherData() {
        disposable += userInfoUseCase.getCurrentWeather("35","139")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                handleCurrentWeatherData(it)
            }
    }

    private fun handleCurrentWeatherData(it: Result<CurrentWeatherData>?) {
        when(it){
            is Result.OnSuccess -> {
                toolbarData.value = ToolbarData(
                    title = "Listing App",
                    weatherVisibility = View.VISIBLE,
                    temperature = it.data.main?.temp?:"",
                    city = it.data.city?:"",
                    weatherDescription = it.data.weather!![0].description?:"",
                    weatherIcon = it.data.weather!![0].icon,
                )

            }

            is Result.OnError -> {
            }
        }
    }


}