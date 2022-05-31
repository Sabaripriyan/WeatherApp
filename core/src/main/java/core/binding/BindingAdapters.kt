package core.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object SetImageBinder {
    @BindingAdapter("android:imageUrl")
    @JvmStatic
    fun setImageWithGlide(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .placeholder(com.example.mylibrary.R.drawable.ic_insert_photo_black_48dp)
                .into(imageView)
        }

    }
}