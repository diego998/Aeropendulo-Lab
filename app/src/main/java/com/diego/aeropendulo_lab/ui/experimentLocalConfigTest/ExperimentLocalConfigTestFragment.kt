package com.diego.aeropendulo_lab.ui.experimentLocalConfigTest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalConfigTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentLocalConfigTestFragment : Fragment() {
    private var _binding: FragmentExperimentLocalConfigTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentLocalConfigTestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}