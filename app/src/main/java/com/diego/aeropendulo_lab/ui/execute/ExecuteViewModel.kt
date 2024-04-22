package com.diego.aeropendulo_lab.ui.execute

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.aeropendulo_lab.data.providers.MeasurementData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExecuteViewModel @Inject constructor(private val sensorPitch: SensorFusion) : ViewModel() {

    private val _state = MutableStateFlow<ExecuteState>(ExecuteState.TapState)
    val state: StateFlow<ExecuteState> = _state

    private val measurements = mutableListOf<MeasurementData>()
    private var starttime: Long = 0L


    private var isListening = false
    var elapsedTime: Long = 0L // Tiempo transcurrido
    private var calibrationValue: Float = 0f

    fun resumeSensor() {
        sensorPitch.onResume()
    }

    fun pauseSensor() {
        sensorPitch.onPause()
        isListening = false
    }

    fun getSensor(time: Long) {
        viewModelScope.launch {
            _state.value = ExecuteState.TapState
            withContext(Dispatchers.IO) {
                while (elapsedTime < time) {
                    val result = withContext(Dispatchers.IO) {
                        sensorPitch.getPitchValue(calibrationValue)
                    }
                    if (isListening) {
                        elapsedTime = System.currentTimeMillis() - starttime
                        measureState(ExecuteState.MeasureState(result, elapsedTime))
                        _state.value = ExecuteState.MeasureState(result, elapsedTime)
                    }
                    kotlinx.coroutines.delay(5)
                }
                isListening = false
            }
            _state.value = ExecuteState.FinishState(measurements)
        }
    }



    fun setCalibration() {
        calibrationValue = sensorPitch.getPitchValue(0f)
    }
    fun setRepeat() {
        measurements.clear()
        elapsedTime = 0L
        _state.value = ExecuteState.TapState
    }

    fun setStartTime(){
        starttime = System.currentTimeMillis() // Tiempo de inicio
        isListening = true
    }

    private fun measureState(state: ExecuteState.MeasureState) {
        val measurement = MeasurementData(state.angle, state.time)
        measurements.add(measurement)
    }
}
