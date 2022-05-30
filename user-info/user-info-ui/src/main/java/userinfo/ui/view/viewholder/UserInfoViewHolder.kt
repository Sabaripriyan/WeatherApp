package userinfo.ui.view.viewholder

import android.content.Context
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user_info_ui.databinding.UserInfoRecyclerItemBinding
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.viewmodel.user_info.UserListViewModel

class UserInfoViewHolder(
    private val binding: UserInfoRecyclerItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userInfoData: UserInfoData, viewModel: UserListViewModel, position: Int) {
        binding.setVariable(BR.viewModel,viewModel)
        binding.setVariable(BR.userInfoData,userInfoData)
        binding.executePendingBindings()
        binding.textName.text = userInfoData.name?.run {
            "$first $last"
        }
        binding.imageProfilePic.apply {
            if (position == 3)
                Glide.with(context)
                    .load(userInfoData.picture?.large)
                    .into(this)
            else

                Glide.with(context)
                    .load(userInfoData.picture?.medium)
                    .into(this)
        }

    }
}