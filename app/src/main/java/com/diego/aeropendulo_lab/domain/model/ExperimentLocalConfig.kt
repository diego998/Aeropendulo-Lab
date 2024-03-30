package com.diego.aeropendulo_lab.domain.model

import com.diego.aeropendulo_lab.R

sealed class ExperimentLocalConfig (val parameter: Int){
    object nameExp: ExperimentLocalConfig(R.string.nameExp)
    object timeExpLocal: ExperimentLocalConfig(R.string.timeExpLocal)
    object refExpLocal: ExperimentLocalConfig(R.string.refExpLocal)
    object loopLocal: ExperimentLocalConfig(R.string.loopLocal)
    object inputExpLocal: ExperimentLocalConfig(R.string.inputExpLocal)
    object inputAmpLocal: ExperimentLocalConfig(R.string.inputAmpLocal)
    object inputFrLocal: ExperimentLocalConfig(R.string.inputFrLocal)
    object controlKpLocal: ExperimentLocalConfig(R.string.controlKpLocal)
    object controlTiLocal: ExperimentLocalConfig(R.string.controlTiLocal)
    object controlTdLocal: ExperimentLocalConfig(R.string.controlTdLocal)
    object controlTsLocal: ExperimentLocalConfig(R.string.controlTsLocal)
}