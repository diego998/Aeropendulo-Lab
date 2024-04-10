package com.diego.aeropendulo_lab.ui.execute

import com.diego.aeropendulo_lab.data.providers.MeasurementData

sealed class ExecuteState {
    object TapState : ExecuteState()
    data class FinishState(val data: List<MeasurementData>) : ExecuteState()
    data class MeasureState(val angle: Float, val time: Long) : ExecuteState()
}
