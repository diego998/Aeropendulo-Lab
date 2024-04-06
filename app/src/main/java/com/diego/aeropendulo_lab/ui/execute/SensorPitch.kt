package com.diego.aeropendulo_lab.ui.execute

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import javax.inject.Inject
import kotlin.math.abs


class SensorPitch @Inject constructor(private val context: Context) : SensorEventListener {


    private lateinit var sensorManager: SensorManager
    private lateinit var mSensorAccelerometer: Sensor
    private lateinit var mSensorMagnetometer: Sensor

    private lateinit var mAccelerometerData: FloatArray
    private lateinit var mMagnetometerData: FloatArray


    private val valueDrift = 0.05f
    private var pitchValue: Float = 0f

    init {
        initSensors()
    }

    private fun initSensors() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            ?: throw IllegalStateException("Accelerometer sensor not available")
        mSensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            ?: throw IllegalStateException("Magnetometer sensor not available")

        mAccelerometerData = FloatArray(3)
        mMagnetometerData = FloatArray(3)
    }

    fun getPitchValue(): Float {
        // Aquí se implementaría la lógica para obtener y calcular el valor del ángulo de pitch
        // utilizando SensorManager.getRotationMatrix() y SensorManager.getOrientation()
        // Luego, retoma el valor del ángulo de pitch calculado
        val rotationMatrix = FloatArray(9)
        val rotationOK = SensorManager.getRotationMatrix(rotationMatrix, null,
            mAccelerometerData, mMagnetometerData)

        val orientationValues = FloatArray(3)
        if (rotationOK) {
            SensorManager.getOrientation(rotationMatrix, orientationValues)
        }

        val azimuth = orientationValues[0]
        var pitch = orientationValues[1]
        var roll = orientationValues[2]

        if (abs(pitch) < valueDrift) {
            pitch = 0f
        }
        if (abs(roll) < valueDrift) {
            roll = 0f
        }

        pitchValue = Math.toDegrees(pitch.toDouble()).toFloat()
        Log.d("VALOR SENSOR",pitchValue.toString())

        return pitchValue // Este es solo un valor de ejemplo, debes reemplazarlo con tu lógica real de cálculo de ángulo
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        // lógica para manejar cambios en los sensores
        when (sensorEvent.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> mAccelerometerData = sensorEvent.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> mMagnetometerData = sensorEvent.values.clone()
            else -> return
        }

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // lógica para manejar cambios en la precisión de los sensores si es necesario
    }

    fun onResume() {
        sensorManager.registerListener(
            this,
            mSensorAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            this,
            mSensorMagnetometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun onPause() {
        sensorManager.unregisterListener(this)
    }
}
