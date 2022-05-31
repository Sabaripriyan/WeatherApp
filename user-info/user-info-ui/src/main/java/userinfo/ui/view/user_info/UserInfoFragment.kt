package userinfo.ui.view.user_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.user_info_ui.R
import com.example.user_info_ui.databinding.FragmentUserInfoBinding
import core.ViewModelDelegate
import core.kotlin.constants.Constants
import core.model.ToolbarData
import core.view.BaseFragment
import core.viewmodel.SharedViewModel
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.viewmodel.user_info.UserInfoViewModel
import java.lang.Exception

class UserInfoFragment : BaseFragment(){

    private lateinit var sharedViewModel: SharedViewModel
    private val userInfoViewModel: UserInfoViewModel by ViewModelDelegate()
    private var _binding: com.example.user_info_ui.databinding.FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    lateinit var userInfo: UserInfoData



    override fun setToolbarData() {
        sharedViewModel.toolbarData.value = ToolbarData(visibility = View.VISIBLE,title = getString(
            com.example.mylibrary.R.string.user_details
        ) )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }?: throw Exception("Activity is null")
        setToolbarData()
        getDataFromArguments()
    }

    private fun getDataFromArguments() {
        arguments?.let {
            userInfo = it.getSerializable(Constants.USER_INFO) as UserInfoData
        }
    }

    private fun setUserData(userInfo: UserInfoData) {
        userInfo.apply {
            userInfoViewModel.userImageUrl.set(userInfo.picture?.large)
            userInfoViewModel.userName.set(this.name?.run {
                "${this.first?:""} ${this.last?:""}"
            })
            userInfoViewModel.email.set(this.email)
            userInfoViewModel.phone.set(this.phone)
            this.location?.let {
                userInfoViewModel.cityAndState.set(it.run {
                    "${this.city}, ${this.state}"
                })
                userInfoViewModel.country.set(it.country?:"")
            }
        }

    }

    private fun observeViewModelLiveData() {
        userInfoViewModel.showProgress.observe(viewLifecycleOwner) { isLoading ->
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
        _binding = FragmentUserInfoBinding.inflate(
            inflater
        ).also {
            it.viewModel = userInfoViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        (activity as AppCompatActivity).supportActionBar?.hide()
        observeViewModelLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserData(userInfo)
        userInfo.location?.coordinates?.let {
            userInfoViewModel.getCurrentWeatherData(it.latitude!!,it.longitude!!)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}