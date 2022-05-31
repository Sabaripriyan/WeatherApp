package core.extensions

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.example.mylibrary.R
import io.reactivex.functions.Cancellable

var dialog: Dialog? = null
fun Context.showAlert(
    title: String,
    message: String,
    positiveAction: (DialogInterface,Int) -> Unit = {dialog,_->dialog.dismiss()},
    negativeAction: (DialogInterface,Int) -> Unit = {dialog,_->dialog.dismiss()},
    @StringRes actionPositiveLabel: Int = R.string.yes,
    @StringRes actionNegativeLabel: Int = R.string.no,
    isCancellable: Boolean = true
    ): Dialog {
    if(dialog?.isShowing == true)
        dialog?.dismiss()
    dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(actionPositiveLabel,positiveAction)
        .setNegativeButton(actionNegativeLabel,negativeAction)
        .setCancelable(isCancellable)
        .show()
    return dialog as Dialog
}