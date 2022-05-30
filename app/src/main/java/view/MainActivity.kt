package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var textTitle:TextView
    private lateinit var textTemp: TextView
    private lateinit var textCity: TextView
    private lateinit var textWeatherDes: TextView
    private lateinit var imageWeatherIcon: ImageView
    private lateinit var group: Group

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupSharedViewModel()
        observeToolbarData()
    }

    private fun setupViews() {
        toolbar = findViewById(R.id.toolbar)
        textTitle = findViewById(R.id.textTitle)
        textTemp = findViewById(R.id.textTemp)
        textCity = findViewById(R.id.textCity)
        textWeatherDes = findViewById(R.id.textDescription)
        imageWeatherIcon = findViewById(R.id.imageIcon)
        group = findViewById(R.id.weatherGroup)
    }

    private fun setupSharedViewModel() {
        viewModel.sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private fun observeToolbarData() {
        viewModel.sharedViewModel.toolbarData.observe(this) {
            toolbar.visibility = it.visibility
            textTitle.text = it.title
            textTemp.text = it.temperature
            textCity.text = it.city
            textWeatherDes.text = it.weatherDescription
            group.visibility = it.weatherVisibility

            imageWeatherIcon.apply {
                Glide.with(context)
                        .load(it.weatherIcon)
                        .into(this)
            }
        }
    }
}