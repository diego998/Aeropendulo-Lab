package com.diego.aeropendulo_lab.data.providers

import com.diego.aeropendulo_lab.domain.model.ControlInfo
import com.diego.aeropendulo_lab.domain.model.ControlInfo.*
import javax.inject.Inject


class SettingsProvider @Inject constructor() {
    fun getSettings():List<ControlInfo>{
        return listOf(
            Info1,
            Info2,
            Info3
        )
    }
}