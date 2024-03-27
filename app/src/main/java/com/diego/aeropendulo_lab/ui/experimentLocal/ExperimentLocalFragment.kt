package com.diego.aeropendulo_lab.ui.experimentLocal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.diego.aeropendulo_lab.R
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalBinding
import com.diego.aeropendulo_lab.databinding.FragmentReportsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperimentLocalFragment : Fragment() {
    private var _binding: FragmentExperimentLocalBinding? = null
    private val binding get() = _binding!!
    private lateinit var navControllerExp: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentLocalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}