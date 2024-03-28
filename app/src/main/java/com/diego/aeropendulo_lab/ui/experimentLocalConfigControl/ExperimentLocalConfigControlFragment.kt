package com.diego.aeropendulo_lab.ui.experimentLocalConfigControl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalConfigControlBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentLocalConfigControlFragment : Fragment() {
    private var _binding: FragmentExperimentLocalConfigControlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentLocalConfigControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}