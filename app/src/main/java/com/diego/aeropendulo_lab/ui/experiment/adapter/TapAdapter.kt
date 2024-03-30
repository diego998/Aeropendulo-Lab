package com.diego.aeropendulo_lab.ui.experiment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.diego.aeropendulo_lab.ui.experimentLocal.ExperimentLocalFragment
import com.diego.aeropendulo_lab.ui.experimentRemote.ExperimentRemoteFragment

class TapAdapter(fa: FragmentManager, internal val totalTab: Int) : FragmentStatePagerAdapter(fa) {
    override fun getCount(): Int {
        return totalTab
    }
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ExperimentRemoteFragment()
            1 -> ExperimentLocalFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}