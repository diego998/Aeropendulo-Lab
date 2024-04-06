package com.diego.aeropendulo_lab.ui.experimentLocal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.diego.aeropendulo_lab.databinding.FragmentExperimentLocalBinding
import com.diego.aeropendulo_lab.ui.experiment.ExperimentFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsExpLocal")

@AndroidEntryPoint
class ExperimentLocalFragment : Fragment() {



    companion object{
        const val TIME_EXP_LOCAL = "tiempo_experimento_local"
    }

    private val experimentLocalViewModel by viewModels<ExperimentLocalViewModel> ()
    private var _binding: FragmentExperimentLocalBinding? = null
    private val binding get() = _binding!!

    private var firstTime:Boolean = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListener()
    }

    private fun initListener() {

        binding.startExperiment.setOnClickListener{
            findNavController().navigate(
                ExperimentFragmentDirections.actionExperimentFragmentToExecuteActivity()
            )
        }

        binding.rangeSliderTiempo.addOnChangeListener { _, value, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                saveTimeLocal(TIME_EXP_LOCAL, value.toInt()) // Corregido: pasando los argumentos correctamente
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExperimentLocalBinding.inflate(inflater, container, false)
        val rootView = binding.root

        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firstTime }.collect { settingsModelExp ->
                if (settingsModelExp != null) {
                    withContext(Dispatchers.Main) {
                        binding.rangeSliderTiempo.setValues(settingsModelExp.timeExpLocal.toFloat())
                        firstTime = !firstTime
                    }
                }
            }
        }

        return rootView
    }


    private suspend fun saveTimeLocal(key: String, value: Int) {
        context?.dataStore?.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsExpLocal?> {
        return context?.dataStore?.data?.map { preferences ->
            SettingsExpLocal(
                timeExpLocal = preferences[intPreferencesKey(TIME_EXP_LOCAL)] ?: 0
            )
        } ?: flowOf(null)
    }

}