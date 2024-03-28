package com.diego.aeropendulo_lab.ui.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.diego.aeropendulo_lab.databinding.FragmentReportsBinding
import com.diego.aeropendulo_lab.ui.experimentLocal.ExperimentLocalFragment
import com.diego.aeropendulo_lab.ui.experimentRemote.ExperimentRemoteFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReportsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}