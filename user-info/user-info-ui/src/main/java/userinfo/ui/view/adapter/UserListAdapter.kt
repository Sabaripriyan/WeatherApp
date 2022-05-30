package userinfo.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.user_info_ui.databinding.UserInfoRecyclerItemBinding
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.view.viewholder.UserInfoViewHolder
import userinfo.ui.viewmodel.user_info.UserListViewModel
import java.util.ArrayList

class UserListAdapter(val userListViewModel: UserListViewModel,
                      val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userInfoList = ArrayList<UserInfoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = UserInfoRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserInfoViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserInfoViewHolder).bind(userInfoList[position],userListViewModel,position)
    }

    override fun getItemCount(): Int {
        return userInfoList.count()
    }

    fun updateList(list: List<UserInfoData>){
        userInfoList.addAll(list)
        notifyDataSetChanged()
        notifyItemInserted(list.size)
    }
}