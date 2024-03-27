package com.diego.aeropendulo_lab.ui.settings

import androidx.lifecycle.ViewModel
import com.diego.aeropendulo_lab.data.providers.SettingsProvider
import com.diego.aeropendulo_lab.domain.model.ControlInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.diego.aeropendulo_lab.domain.model.ControlInfo.*

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsProvider: SettingsProvider) : ViewModel(){

    private var _info = MutableStateFlow<List<ControlInfo>>(emptyList())
    val info: StateFlow<List<ControlInfo>> = _info

    init {
        _info.value = settingsProvider.getSettings()

    }
}