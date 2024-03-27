package com.diego.aeropendulo_lab.ui.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diego.aeropendulo_lab.R
import com.diego.aeropendulo_lab.domain.model.ControlInfo

class SettingsAdapter (private var controlList:List<ControlInfo> = emptyList(),
    private val onItemSelected:(ControlInfo) -> Unit):
    RecyclerView.Adapter<SettingsViewHolder>(){

    fun updateList(list: List<ControlInfo>){
        controlList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        )
    }

    override fun getItemCount() = controlList.size


    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.render(controlList[position], onItemSelected)
    }
}