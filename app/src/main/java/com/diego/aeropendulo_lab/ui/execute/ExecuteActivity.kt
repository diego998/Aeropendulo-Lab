package com.diego.aeropendulo_lab.ui.execute

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.diego.aeropendulo_lab.databinding.ActivityExecuteBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExecuteActivity : AppCompatActivity() {

    private val time: Long = 30000 //Dato que  se ingresa en el GUI


    private lateinit var binding: ActivityExecuteBinding
    private val executeViewModel: ExecuteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecuteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }


    override fun onResume() {
        super.onResume()
        executeViewModel.resumeSensor()

    }

    override fun onPause() {
        super.onPause()
        executeViewModel.pauseSensor()
    }

    private fun initUI() {
        initListener()
        initUIState(time)

    }

    private fun initListener() {
        binding.ivBack.setOnClickListener{onBackPressed()}
    }

    private fun initUIState(time: Long) {
        lifecycleScope.launch {
            executeViewModel.state.collect { state ->
                when (state) {
                    is ExecuteState.FinishState -> finishstate(state)
                    ExecuteState.TapState -> tapState(time)
                    is ExecuteState.MeasureState -> measureState(state)
                }
            }
        }
    }

    private fun measureState(state: ExecuteState.MeasureState) {

        binding.instructionsLayout.visibility = View.GONE
        binding.measurementLayout.visibility = View.VISIBLE
        binding.finishLayout.visibility = View.GONE
        // Acceder a los datos proporcionados en el estado de éxito (por ejemplo, el ángulo medido)
        val angle = String.format("%.2f", state.angle) // Formatear el ángulo con dos decimales
        Log.d("VALOR GRAFICADO", angle)
        // Actualizar la interfaz de usuario con los datos obtenidos
        binding.txtAngulo.text = "Ángulo medido: $angle"
        binding.txtTime.text = "Tiempo: ${state.time}"
    }

    private fun finishstate(state: ExecuteState.FinishState) {
        // Cambiar la visibilidad para mostrar el estado de medición finalizada
        binding.instructionsLayout.visibility = View.GONE
        binding.measurementLayout.visibility = View.GONE
        binding.finishLayout.visibility = View.VISIBLE

        // Configurar el LineChart utilizando binding
        val lineChart = binding.lineChart
        setupLineChart(lineChart)

        // Configurar los datos para el gráfico
        val entries = ArrayList<Entry>()
        for ((index, measurement) in state.data.withIndex()) {
            // Convertir el tiempo a segundos
            val timeInSeconds = measurement.time.toFloat()
            // Agregar el punto de datos al conjunto de entradas
            entries.add(Entry(timeInSeconds, measurement.angle))
        }

        val dataSet = LineDataSet(entries, "Ángulo Medido")
        dataSet.color = ColorTemplate.VORDIPLOM_COLORS[0]
        dataSet.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0])
        val lineData = LineData(dataSet)

        // Configurar los datos en el gráfico
        lineChart.data = lineData
        lineChart.invalidate()


        // Configurar el listener del botón de guardar
        binding.saveButton.setOnClickListener {
            // Aquí puedes implementar la lógica para guardar el experimento
            // Por ejemplo, guardar los datos en una base de datos o en algún otro lugar
            // Después de guardar, puedes realizar cualquier otra acción necesaria, como mostrar un mensaje de éxito, etc.
        }

        // Configurar el listener del botón de repetir
        binding.repeatButton.setOnClickListener {
            executeViewModel.setRepeat()
            // Aquí puedes implementar la lógica para repetir el experimento
            // Por ejemplo, restablecer la actividad o cualquier otra acción necesaria para iniciar un nuevo experimento
        }
    }

    private fun tapState(time: Long) {
        // Cambiar la visibilidad para mostrar el estado de medición en curso
        binding.instructionsLayout.visibility = View.VISIBLE
        binding.measurementLayout.visibility = View.GONE
        binding.finishLayout.visibility = View.GONE
        // Aquí puedes agregar cualquier otra acción necesaria para este estado

        executeViewModel.getSensor(time)


        binding.savCalibration.setOnClickListener {
            executeViewModel.setCalibration()
        }

        binding.initExp.setOnClickListener {
            executeViewModel.setStartTime()
        }
    }

    private fun setupLineChart(lineChart: LineChart) {
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.description.isEnabled = false

        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = lineChart.axisLeft
        leftAxis.setDrawGridLines(false)

        val rightAxis: YAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        lineChart.legend.isEnabled = false
    }
}