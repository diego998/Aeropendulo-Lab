package com.diego.aeropendulo_lab.ui.execute

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExecuteViewModel @Inject constructor(private val sensorPitch: SensorPitch) : ViewModel() {

    private val _state = MutableStateFlow<ExecuteState>(ExecuteState.TapState)
    val state: StateFlow<ExecuteState> = _state

    private var isListening = false

    fun resumeSensor() {

        if (!isListening) {
            isListening = true
            sensorPitch.onResume()
            getSensor()
        }
    }

    fun pauseSensor() {
        sensorPitch.onPause()
        isListening = false
    }

    fun getSensor() {
        viewModelScope.launch {
            _state.value = ExecuteState.TapState
            while (isListening) {
                val result = withContext(Dispatchers.IO) {
                    sensorPitch.getPitchValue()
                }
                Log.d("VALOR VIEWHOLDER",result.toString())
                _state.value = ExecuteState.SeccessState(result)
                // Añade un pequeño retraso si es necesario para no sobrecargar el procesador
                // con actualizaciones demasiado frecuentes
                kotlinx.coroutines.delay(20) // Espera 100 milisegundos antes de la próxima actualización
            }
        }
    }
}
