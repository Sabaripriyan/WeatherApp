package core.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.text.Editable
import android.view.LayoutInflater
import android.view.Window
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    companion object {
        const val PERMISSION_ALL = 100
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
   var dialog: Dialog? = null

    abstract fun setToolbarData()

    fun showLoader() {
        hideLoader()
        createLoader()
    }

    fun isLoading(): Boolean {
        if(dialog!=null)
            return dialog!!.isShowing
        else
            return false
    }

    fun hideLoader() {
        if(isLoading())
            dialog?.cancel()
    }

    private fun createLoader(): Dialog{
        dialog = Dialog(requireActivity(), android.R.style.Theme_Black)
        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_progress_dialog,null)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setContentView(view)
        dialog?.setCancelable(false)
        dialog?.show()
        return dialog as Dialog
    }

    fun requestPermissions(context: Context,
                           permissions: Array<String>,
                           activityResultLauncher: ActivityResultLauncher<Array<String>>,
                           callback: PermissionsGrantedCallback
    ){
        if (!hasPermissions(context,permissions)) {
            activityResultLauncher.launch(permissions)
        } else {
            callback.permissionsGranted()
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    interface PermissionsGrantedCallback {
        fun permissionsGranted()
    }
}