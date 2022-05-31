package userinfo.ui.view.user_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.user_info_ui.R
import com.example.user_info_ui.databinding.FragmentSplashBinding
import com.example.user_info_ui.databinding.FragmentUserListBinding
import core.ViewModelDelegate
import core.kotlin.constants.Constants.USER_LIST_VISIBLE_THRESHOLD
import core.model.ToolbarData
import core.view.BaseFragment
import core.viewmodel.SharedViewModel
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.view.adapter.UserListAdapter
import userinfo.ui.viewmodel.splash.SplashViewModel
import userinfo.ui.viewmodel.user_info.UserListViewModel
import java.lang.Exception

class UserListFragment : BaseFragment(), UserListAdapter.OnUserInfoClicked {

    private lateinit var sharedViewModel: SharedViewModel
    private val userListViewModel: UserListViewModel by ViewModelDelegate()
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    lateinit var userListAdapter: UserListAdapter


    override fun setToolbarData() {
        sharedViewModel.toolbarData.value = ToolbarData(visibility = View.VISIBLE,title = "" )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }?: throw Exception("Activity is null")
        setToolbarData()
    }

    private fun observeViewModelLiveData() {
        userListViewModel.toolbarData.observe(this) {
            sharedViewModel.toolbarData.value = it
        }

        userListViewModel.userInfoList.observe(this){
            if(userListViewModel.pageIndex != 1)
                userListAdapter.removeProgress()
            userListAdapter.updateList(it)
        }

        userListViewModel.showProgress.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading == true)
                showLoader()
            else
                hideLoader()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(
            inflater
        ).also {
            it.viewModel = userListViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        (activity as AppCompatActivity).supportActionBar?.hide()
        setRecyclerAdapter()
        setScrollListener()
        observeViewModelLiveData()
        userListViewModel.getUserInfoList(true)
        userListViewModel.getCurrentWeatherData()
        return binding.root
    }


    private fun setRecyclerAdapter(){
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        userListAdapter = UserListAdapter(userListViewModel = userListViewModel, requireContext(),this)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
        binding.recyclerView.adapter = userListAdapter
    }

    private fun setScrollListener(){
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleThreshold = USER_LIST_VISIBLE_THRESHOLD * layoutManager.spanCount
                    val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPositions(null)
                    val lastVisibleItem = getLastVisibleItem(lastVisibleItemPosition)
                    if(!userListViewModel.isLoading && totalItemCount <= lastVisibleItem + visibleThreshold){
                        userListAdapter.addProgress()
                        userListViewModel.loadMore()
                    }
            }
        })
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun userInfoClicked(userInfo: UserInfoData) {
        val bundle = bundleOf(Pair("user_info",userInfo))
        findNavController().navigate(R.id.action_user_list_to_user_info,bundle)
    }
}