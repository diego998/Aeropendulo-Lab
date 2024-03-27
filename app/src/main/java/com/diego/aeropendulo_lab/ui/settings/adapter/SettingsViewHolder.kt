package com.diego.aeropendulo_lab.ui.settings.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.diego.aeropendulo_lab.databinding.ItemInfoBinding
import com.diego.aeropendulo_lab.domain.model.ControlInfo

class SettingsViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemInfoBinding.bind(view)
    fun render(controlInfo: ControlInfo, onItemSelected: (ControlInfo) -> Unit){
        val context = binding.tvInfo.context
        binding.ivInfo.setImageResource(controlInfo.img)
        binding.tvInfo.text = context.getString(controlInfo.name)

        binding.parent.setOnClickListener{
            onItemSelected(controlInfo)
        }
    }
}