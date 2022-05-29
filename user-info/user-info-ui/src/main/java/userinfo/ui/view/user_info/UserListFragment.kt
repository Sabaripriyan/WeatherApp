package userinfo.ui.view.user_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.user_info_ui.databinding.FragmentSplashBinding
import com.example.user_info_ui.databinding.FragmentUserListBinding
import core.ViewModelDelegate
import core.model.ToolbarData
import core.view.BaseFragment
import core.viewmodel.SharedViewModel
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
        userListViewModel.getUserInfoList()
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
        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}