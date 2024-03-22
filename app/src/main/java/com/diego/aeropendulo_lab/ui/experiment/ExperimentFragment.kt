package com.diego.aeropendulo_lab.ui.experiment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diego.aeropendulo_lab.databinding.FragmentExperimentBinding

class ExperimentFragment : Fragment() {

    private var _binding: FragmentExperimentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}