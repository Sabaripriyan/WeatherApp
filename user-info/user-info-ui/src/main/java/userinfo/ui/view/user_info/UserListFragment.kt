package userinfo.ui.view.user_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.user_info_ui.databinding.FragmentSplashBinding
import com.example.user_info_ui.databinding.FragmentUserListBinding
import core.ViewModelDelegate
import core.model.ToolbarData
import core.view.BaseFragment
import core.viewmodel.SharedViewModel
import userinfo.ui.view.adapter.UserListAdapter
import userinfo.ui.viewmodel.splash.SplashViewModel
import userinfo.ui.viewmodel.user_info.UserListViewModel
import java.lang.Exception

class UserListFragment : BaseFragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private val userListViewModel: UserListViewModel by ViewModelDelegate()
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    override fun setToolbarData() {
        sharedViewModel.toolbarData.value = ToolbarData(visibility = View.VISIBLE,title = "" )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }?: throw Exception("Activity is null")
        setToolbarData()
        observeViewModelLiveData()
    }

    private fun observeViewModelLiveData() {
        userListViewModel.toolbarData.observe(this) {
            sharedViewModel.toolbarData.value = it
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListViewModel.getUserInfoList()
        userListViewModel.getCurrentWeatherData()
    }

    fun setRecyclerAdapter(){
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        userListViewModel.userListAdapter = UserListAdapter(userListViewModel = userListViewModel, requireContext())
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
        binding.recyclerView.adapter = userListViewModel.userListAdapter
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}