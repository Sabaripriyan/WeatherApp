package core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import core.model.ToolbarData

class SharedViewModel: ViewModel() {

    val toolbarData = MutableLiveData<ToolbarData>()
}