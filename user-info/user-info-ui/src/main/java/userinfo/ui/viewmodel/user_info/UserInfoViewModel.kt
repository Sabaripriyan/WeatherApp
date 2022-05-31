package userinfo.ui.viewmodel.user_info

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import core.kotlin.Result
import core.kotlin.whileSubscribed
import core.model.ToolbarData
import core.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import userinfo.domain.model.weather.CurrentWeatherData
import userinfo.domain.usecase.UserInfoUseCase
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
): BaseViewModel() {

    val userName = ObservableField<String>()
    val userImageUrl = ObservableField<String>()
    val email = ObservableField<String>()
    val phone = ObservableField<String>()
    val cityAndState = ObservableField<String>()
    val country = ObservableField<String>()
    val temperature = ObservableField<String>()
    val weatherDescription = ObservableField<String>()
    val weatherIconUrl = ObservableField<String>()

    fun getCurrentWeatherData(lat: String, lon: String) {
        disposable += userInfoUseCase.getCurrentWeather(lat,lon)
            .subscribeOn(Schedulers.io())
            .whileSubscribed{showProgress.postValue(it)}
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                handleCurrentWeatherData(it)
            }
    }

    private fun handleCurrentWeatherData(it: Result<CurrentWeatherData>?) {
        when(it){
            is Result.OnSuccess -> {
                setWeatherData(it.data)
            }

            is Result.OnError -> {
            }
        }
    }

    private fun setWeatherData(currentWeatherData: CurrentWeatherData) {
        currentWeatherData.apply {
            temperature.set(this.main?.temp)
            this.weather?.get(0)?.let {
                weatherDescription.set(it.description)
                weatherIconUrl.set(it.icon)
            }
        }
    }
}