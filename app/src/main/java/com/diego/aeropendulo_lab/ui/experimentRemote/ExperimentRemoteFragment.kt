package com.diego.aeropendulo_lab.ui.experimentRemote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diego.aeropendulo_lab.R
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalBinding
import com.diego.aeropendulo_lab.databinding.FragmentExperimentRemoteBinding
import com.diego.aeropendulo_lab.ui.experiment.ExperimentFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentRemoteFragment : Fragment() {
    private var _binding: FragmentExperimentRemoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentRemoteBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }
}