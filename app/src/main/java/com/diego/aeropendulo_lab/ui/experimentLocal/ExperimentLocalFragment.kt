package com.diego.aeropendulo_lab.ui.experimentLocal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentLocalFragment : Fragment() {
    private var _binding: FragmentExperimentLocalBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentLocalBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.cardView2.setOnClickListener {
            findNavController().navigate(
                ExperimentLocalFragmentDirections.actionExperimentLocalFragmentToExperimentLocalConfigInputFragment()
            )
        }

        return view
    }
}