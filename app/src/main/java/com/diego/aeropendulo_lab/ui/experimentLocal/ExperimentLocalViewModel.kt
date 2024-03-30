package com.diego.aeropendulo_lab.ui.experimentLocal

import androidx.lifecycle.ViewModel
import com.diego.aeropendulo_lab.domain.model.ExperimentLocalConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExperimentLocalViewModel @Inject constructor() : ViewModel(){

    private val _ExperimentLocalConfig = MutableStateFlow<List<ExperimentLocalConfig>>(emptyList())
    val experimentlocalconfig:StateFlow<List<ExperimentLocalConfig>> = _ExperimentLocalConfig

    init {
        _ExperimentLocalConfig.value = listOf(

        )
    }

}