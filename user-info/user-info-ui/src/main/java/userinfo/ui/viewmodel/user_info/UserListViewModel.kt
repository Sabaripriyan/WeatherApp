package userinfo.ui.viewmodel.user_info

import android.view.View
import androidx.lifecycle.MutableLiveData
import core.kotlin.Result
import core.kotlin.whileSubscribed
import core.model.ToolbarData
import core.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import userinfo.domain.model.user_info.UserInfoApiResponseData
import userinfo.domain.model.user_info.UserInfoData
import userinfo.domain.model.weather.CurrentWeatherData
import userinfo.domain.usecase.UserInfoUseCase
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
): BaseViewModel() {


    val toolbarData = MutableLiveData<ToolbarData>()
    val userInfoList =  MutableLiveData<List<UserInfoData>>()
    var pageIndex = 1
    var isLoading = false


    fun loadMore(){
        pageIndex++
        getUserInfoList()
        isLoading = true
    }
    fun getUserInfoList(isFirstCall: Boolean = false) {
        disposable += userInfoUseCase.getUserInfo(pageIndex)
            .subscribeOn(Schedulers.io())
            .whileSubscribed { showProgress.postValue(isFirstCall&&it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                handleUserInfo(it)
            }
    }

    private fun handleUserInfo(it: Result<UserInfoApiResponseData>?) {
        when(it){
            is Result.OnSuccess -> {
                userInfoList.postValue(it.data.results?: emptyList())
                isLoading = false
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