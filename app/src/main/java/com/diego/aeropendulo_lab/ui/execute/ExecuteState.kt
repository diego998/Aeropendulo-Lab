package com.diego.aeropendulo_lab.ui.execute

sealed class ExecuteState {
    object TapState : ExecuteState()
    object FinishState : ExecuteState()
    data class SeccessState(val angle: Float) : ExecuteState()
}
