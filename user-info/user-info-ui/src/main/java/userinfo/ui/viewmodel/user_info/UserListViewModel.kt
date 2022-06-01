package userinfo.ui.viewmodel.user_info

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.user_info_ui.R
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

    init {
        getUserInfoList(true)
    }

    val toolbarData = MutableLiveData<ToolbarData>()
    val userInfoList =  MutableLiveData<List<UserInfoData>>()
    var pageIndex = 1
    var isLoading = false
    val removeLocationClient = MutableLiveData<Boolean>()


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

    fun getCurrentWeatherData(context:Context,lat: String, lon: String) {
        disposable += userInfoUseCase.getCurrentWeather(lat,lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                handleCurrentWeatherData(context,it)
            }
    }

    private fun handleCurrentWeatherData(context: Context, it: Result<CurrentWeatherData>?) {
        when(it){
            is Result.OnSuccess -> {
                toolbarData.value = ToolbarData(
                    title = context.getString(com.example.mylibrary.R.string.app_name),
                    weatherVisibility = View.VISIBLE,
                    temperature = it.data.main?.temp?:"",
                    city = it.data.city?:"",
                    weatherDescription = it.data.weather!![0].description?:"",
                    weatherIcon = it.data.weather!![0].icon,
                )
                removeLocationClient.value = true

            }

            is Result.OnError -> {
            }
        }
    }


}