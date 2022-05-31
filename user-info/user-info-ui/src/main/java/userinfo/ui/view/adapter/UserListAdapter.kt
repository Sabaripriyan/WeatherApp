package userinfo.ui.view.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.user_info_ui.R
import com.example.user_info_ui.databinding.LayoutProgressBinding
import com.example.user_info_ui.databinding.UserInfoRecyclerItemBinding
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.view.viewholder.ProgressViewHolder
import userinfo.ui.view.viewholder.UserInfoViewHolder
import userinfo.ui.viewmodel.user_info.UserListViewModel
import java.util.ArrayList
const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_PROGRESS = 1

class UserListAdapter(private val userListViewModel: UserListViewModel,
                      private val context: Context,
                      private val onUserInfoClicked: OnUserInfoClicked
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userInfoList = ArrayList<UserInfoData?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType != VIEW_TYPE_ITEM){
            val binding = LayoutProgressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ProgressViewHolder( binding)
        }
        else {
            val binding = UserInfoRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            UserInfoViewHolder(binding,context)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is UserInfoViewHolder){
            (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).apply {
                isFullSpan = false

            }
            holder.bind(userInfoList[position]!!,userListViewModel,onUserInfoClicked)
        }

        else
        (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams). isFullSpan = true
    }

    override fun getItemCount(): Int {
        return userInfoList.count()
    }

    override fun getItemViewType(position: Int): Int {
        if(userInfoList[position] == null)
            return VIEW_TYPE_PROGRESS
        else
            return VIEW_TYPE_ITEM
    }

    fun updateList(list: List<UserInfoData?>){
        userInfoList.addAll(list)
        notifyDataSetChanged()
        notifyItemInserted(list.size)
    }

    fun addProgress(){
        userInfoList.add(null)
        notifyItemInserted(userInfoList.size)
    }

    fun removeProgress() {
        if(userInfoList.isNotEmpty() && userInfoList[userInfoList.lastIndex] == null){
            userInfoList.removeLast()
            notifyItemRemoved(userInfoList.size)
        }
    }

    interface OnUserInfoClicked {
        fun userInfoClicked(userInfo: UserInfoData)
    }
}