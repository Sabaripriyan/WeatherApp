package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.base.R
import com.example.base.databinding.ActivityMainBinding
import core.ViewModelDelegate
import core.view.BaseActivity
import viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    val viewModel: MainActivityViewModel by ViewModelDelegate()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}