package com.diego.aeropendulo_lab.ui.experiment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.diego.aeropendulo_lab.databinding.FragmentExperimentBinding
import com.diego.aeropendulo_lab.ui.experimentLocal.ExperimentLocalFragment
import com.diego.aeropendulo_lab.ui.experimentRemote.ExperimentRemoteFragment
import com.google.android.material.tabs.TabLayoutMediator
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val pagerAdapter = MyPagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter

        // Configure TabLayout with ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Experimento remoto"
                1 -> "Experimento local"
                else -> "Undefined"
            }
        }.attach()

        return view
    }

    private inner class MyPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return 2 // Número de pestañas
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ExperimentRemoteFragment()
                1 -> ExperimentLocalFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }
}