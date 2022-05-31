package core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
    var disposable: CompositeDisposable = CompositeDisposable()

    val showProgress = MutableLiveData<Boolean>(false)

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}