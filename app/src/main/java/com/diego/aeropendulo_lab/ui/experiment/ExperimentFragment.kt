package com.diego.aeropendulo_lab.ui.experiment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diego.aeropendulo_lab.databinding.FragmentExperimentBinding
import com.google.android.material.tabs.TabLayout
import com.diego.aeropendulo_lab.ui.experiment.adapter.TapAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentFragment : Fragment() {

    private var _binding: FragmentExperimentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        tablayoutfunc()
    }

    private fun tablayoutfunc() {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        val adapter = TapAdapter(childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}