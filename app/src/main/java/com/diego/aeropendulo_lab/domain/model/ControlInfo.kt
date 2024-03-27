package com.diego.aeropendulo_lab.domain.model

import com.diego.aeropendulo_lab.R

sealed class ControlInfo (val img:Int,val name:Int){
    data object Info1: ControlInfo(R.drawable.info1, R.string.info1)
    data object Info2: ControlInfo(R.drawable.info2, R.string.info2)
    data object Info3: ControlInfo(R.drawable.info3, R.string.info3)

}
