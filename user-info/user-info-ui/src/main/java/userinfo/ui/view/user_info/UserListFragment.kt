package userinfo.ui.view.user_info

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.user_info_ui.R
import com.example.user_info_ui.databinding.FragmentUserListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import core.ViewModelDelegate
import core.extensions.showAlert
import core.kotlin.constants.Constants.USER_INFO
import core.kotlin.constants.Constants.USER_LIST_VISIBLE_THRESHOLD
import core.model.ToolbarData
import core.view.BaseFragment
import core.viewmodel.SharedViewModel
import userinfo.domain.model.user_info.UserInfoData
import userinfo.ui.view.adapter.UserListAdapter
import userinfo.ui.viewmodel.user_info.UserListViewModel


class UserListFragment : BaseFragment(), UserListAdapter.OnUserInfoClicked, BaseFragment.PermissionsGrantedCallback {

    private lateinit var sharedViewModel: SharedViewModel
    private val userListViewModel: UserListViewModel by ViewModelDelegate()
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    lateinit var userListAdapter: UserListAdapter
    var fusedLocationProviderClient: FusedLocationProviderClient? = null


    override fun setToolbarData() {
        sharedViewModel.toolbarData.value = ToolbarData(visibility = View.VISIBLE,title = getString(
            com.example.mylibrary.R.string.app_name) )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }?: throw Exception("Activity is null")
        setToolbarData()
    }

    private fun observeViewModelLiveData() {
        userListViewModel.toolbarData.observe(viewLifecycleOwner) {
            sharedViewModel.toolbarData.value = it
        }

        userListViewModel.userInfoList.observe(viewLifecycleOwner){
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

        userListViewModel.removeLocationClient.observe(viewLifecycleOwner) {
            if(it){
                fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
            }

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
        checkLocationPermissions()
        return binding.root
    }

    private fun checkLocationPermissions(){
        requestPermissions(
            context = requireContext(),
            permissions = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            activityResultLauncher = requestMultiplePermissionsLauncher,
            callback = this
        )
    }

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions?.entries?.any { it.value == false }?.let {
            if(it)
             requireContext().showAlert(
                 title = getString(com.example.mylibrary.R.string.location_permission_needed),
                 message = getString(com.example.mylibrary.R.string.location_permission_necessity),
                 positiveAction = { _dialog,_->
                     _dialog.dismiss()
                     checkLocationPermissions()
                 },
                 negativeAction = { _dialog, _ ->
                     _dialog.dismiss()
                 },
                 isCancellable = false
             )
            else
                getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        if(isLocationEnabled()){
            LocationServices.getFusedLocationProviderClient(requireContext())
                .lastLocation.addOnCompleteListener { p0 ->
                    val location = p0.result
                    if (location == null) {

                    } else {
                        userListViewModel.getCurrentWeatherData(
                            context = requireContext(),
                            lat = location.latitude.toString(),
                            lon = location.longitude.toString())
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocation(){
        val locationRequest = com.google.android.gms.location.LocationRequest().apply {
            priority = LocationRequest.QUALITY_HIGH_ACCURACY
            interval = 5
            fastestInterval = 0
            numUpdates = 1
            LocationServices.getFusedLocationProviderClient(requireContext())
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient?.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper()!!)

    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            p0.lastLocation.let {
                userListViewModel.getCurrentWeatherData(
                    context = requireContext(),
                    lat = it.latitude.toString(),
                    lon = it.longitude.toString())
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
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
        val bundle = bundleOf(Pair(USER_INFO,userInfo))
        findNavController().navigate(R.id.action_user_list_to_user_info,bundle)
    }

    override fun permissionsGranted() {
        getLocation()
    }
}