package com.diego.aeropendulo_lab.ui.execute

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.diego.aeropendulo_lab.databinding.ActivityExecuteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExecuteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityExecuteBinding
    private val executeViewModel: ExecuteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecuteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }


    override fun onResume() {
        super.onResume()
        executeViewModel.resumeSensor()

    }

    override fun onPause() {
        super.onPause()
        executeViewModel.pauseSensor()
    }

    private fun initUI() {
        initListener()
        initUIState()

    }

    private fun initListener() {
        binding.ivBack.setOnClickListener{onBackPressed()}
    }

    private fun initUIState() {
        lifecycleScope.launch {
            executeViewModel.state.collect { state ->
                when (state) {
                    is ExecuteState.FinishState -> finishstate()
                    ExecuteState.TapState -> tapState()
                    is ExecuteState.SeccessState -> successState(state)
                }
            }
        }
    }

    private fun successState(state: ExecuteState.SeccessState) {
        // Acceder a los datos proporcionados en el estado de éxito (por ejemplo, el ángulo medido)
        val angle = state.angle.toString()
        Log.d("VALOR GRAFICADO",angle)
        // Actualizar la interfaz de usuario con los datos obtenidos
        binding.txtAngulo.text = "Ángulo medido: $angle"
    }

    private fun finishstate() {

    }

    private fun tapState() {

    }
}