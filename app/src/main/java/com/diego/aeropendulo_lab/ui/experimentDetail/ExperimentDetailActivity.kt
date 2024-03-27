package com.diego.aeropendulo_lab.ui.experimentDetail


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.diego.aeropendulo_lab.R
import com.diego.aeropendulo_lab.databinding.ActivityExperimentDetailBinding
import com.diego.aeropendulo_lab.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExperimentDetailBinding
    private lateinit var navControllerExp: NavController
    private val ExperimentDetailViewModel:ExperimentDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityExperimentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        //:)
    }

    private fun initUI() {
        initNavigationExp()
    }
    private fun initNavigationExp() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewExpDetail) as NavHostFragment
        navControllerExp = navHost.navController
        binding.expNavView.setupWithNavController(navControllerExp)
    }
}
