package core.view

import android.app.Dialog
import android.text.Editable
import android.view.LayoutInflater
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var dialog: Dialog

    abstract fun setToolbarData()

    fun showLoader() {
        if(!dialog.isShowing)
            createLoader()
    }

    fun hideLoader() {
        if(dialog.isShowing)
            dialog.cancel()
    }

    private fun createLoader(): Dialog{
        dialog = Dialog(requireActivity(), android.R.style.Theme_Black)
        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_progress_dialog,null)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}