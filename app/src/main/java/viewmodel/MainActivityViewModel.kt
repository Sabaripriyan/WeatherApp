package viewmodel

import core.viewmodel.BaseViewModel
import core.viewmodel.SharedViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(): BaseViewModel() {
    lateinit var sharedViewModel: SharedViewModel

}