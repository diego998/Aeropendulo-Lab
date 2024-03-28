package com.diego.aeropendulo_lab.ui.experimentLocalConfigInput

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalConfigInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentLocalConfigInputFragment : Fragment() {
    private var _binding: FragmentExperimentLocalConfigInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentLocalConfigInputBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}