package userinfo.ui.view.splash

import core.ViewModelDelegate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.user_info_ui.databinding.FragmentSplashBinding
import core.model.ToolbarData
import core.viewmodel.SharedViewModel
import userinfo.ui.viewmodel.splash.SplashViewModel
import core.view.BaseFragment
import java.lang.Exception


class SplashFragment : BaseFragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private val splashViewModel: SplashViewModel by ViewModelDelegate()
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun setToolbarData() {
        sharedViewModel.toolbarData.value = ToolbarData(visibility = 0,title = "" )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }?: throw Exception("Activity is null")
        setToolbarData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(
            inflater
        ).also {
            it.viewModel = splashViewModel
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