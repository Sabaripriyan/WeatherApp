package userinfo.ui.view.viewholder

import android.content.Context
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user_info_ui.R
import com.example.user_info_ui.databinding.LayoutProgressBinding
import com.example.user_info_ui.databinding.UserInfoRecyclerItemBinding
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.view.adapter.UserListAdapter
import userinfo.ui.viewmodel.user_info.UserListViewModel

class UserInfoViewHolder(
    private val binding: UserInfoRecyclerItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userInfoData: UserInfoData, viewModel: UserListViewModel, onUserInfoClicked: UserListAdapter.OnUserInfoClicked) {
        binding.setVariable(BR.viewModel,viewModel)
        binding.setVariable(BR.userInfoData,userInfoData)
        binding.executePendingBindings()
        binding.textName.text = userInfoData.name?.run {
            "$first $last"
        }
        binding.imageProfilePic.apply {
                Glide.with(context)
                    .load(userInfoData.picture?.medium)
                    .placeholder(com.example.mylibrary.R.drawable.ic_insert_photo_black_48dp)
                    .into(this)
        }

        binding.root.setOnClickListener {
            onUserInfoClicked.userInfoClicked(userInfoData)
        }

    }
}

class ProgressViewHolder(private val binding: LayoutProgressBinding): RecyclerView.ViewHolder(binding.root){

}