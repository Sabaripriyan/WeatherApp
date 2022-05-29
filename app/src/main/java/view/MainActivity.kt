package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.base.R
import com.example.base.databinding.ActivityMainBinding
import core.ViewModelDelegate
import core.view.BaseActivity
import core.viewmodel.SharedViewModel
import viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    val viewModel: MainActivityViewModel by ViewModelDelegate()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSharedViewModel()
        observeToolbarData()
    }

    private fun setupSharedViewModel() {
        viewModel.sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private fun observeToolbarData() {
        viewModel.sharedViewModel.toolbarData.observe(this) {
            binding.toolbar.visibility = it.visibility
        }
    }
}