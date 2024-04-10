package com.diego.aeropendulo_lab.ui.execute

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class SensorFusion @Inject constructor(private val context: Context) : SensorEventListener {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mSensorAccelerometer: Sensor
    private lateinit var mSensorMagnetometer: Sensor
    private lateinit var mSensorGyroscope: Sensor

    // Angular speeds from gyro
    private var gyro = FloatArray(3)

    // Rotation matrix from gyro data
    private var gyroMatrix = FloatArray(9)

    // Orientation angles from gyro matrix
    private var gyroOrientation = FloatArray(3)

    // Magnetic field vector
    private var magnet = FloatArray(3)

    // Accelerometer vector
    private var accel = FloatArray(3)

    // Orientation angles from accelerometer and magnetometer
    private var accMagOrientation = FloatArray(3)

    // Final orientation angles from sensor fusion
    private var fusedOrientation = FloatArray(3)

    // Accelerometer and magnetometer based rotation matrix
    private var rotationMatrix = FloatArray(9)

    private val NS2S = 1.0f / 1000000000.0f
    private val TIME_CONSTANT = 5
    private val FILTER_COEFFICIENT = 0.90f
    private var timestamp: Float = 0f
    private var initState = true
    private val fuseTimer = Timer()
    val EPSILON = 0.000000001f
    private val valueDrift = 0.001f


    companion object {

    }

    init {
        initSensors()
    }

    private fun initSensors() {

        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            ?: throw IllegalStateException("Accelerometer sensor not available")
        mSensorMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            ?: throw IllegalStateException("Magnetometer sensor not available")
        mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            ?: throw IllegalStateException("Gyroscope sensor not available")

        gyroOrientation[0] = 0.0f
        gyroOrientation[1] = 0.0f
        gyroOrientation[2] = 0.0f

        // Initialize gyroMatrix with identity matrix
        gyroMatrix[0] = 1.0f
        gyroMatrix[1] = 0.0f
        gyroMatrix[2] = 0.0f
        gyroMatrix[3] = 0.0f
        gyroMatrix[4] = 1.0f
        gyroMatrix[5] = 0.0f
        gyroMatrix[6] = 0.0f
        gyroMatrix[7] = 0.0f
        gyroMatrix[8] = 1.0f

        // Get SensorManager and initialize sensor listeners
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        initListeners()
    }

    private fun initListeners() {
        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )

        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED),
            SensorManager.SENSOR_DELAY_FASTEST
        )

        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, accel, 0, 3)
                calculateAccMagOrientation()
            }
            Sensor.TYPE_GYROSCOPE -> {
                gyroFunction(event)
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, magnet, 0, 3)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun calculateAccMagOrientation() {

        if (SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet)) {
            SensorManager.getOrientation(rotationMatrix, accMagOrientation)
        }
        Log.d("VALOR OTIENTATION ACCMAG",accMagOrientation[1].toString())

    }
    private fun getRotationVectorFromGyro(gyroValues: FloatArray, deltaRotationVector: FloatArray, timeFactor: Float) {
        val normValues = FloatArray(3)

        // Calculate the angular speed of the sample
        val omegaMagnitude = kotlin.math.sqrt(gyroValues[0] * gyroValues[0] +
                gyroValues[1] * gyroValues[1] +
                gyroValues[2] * gyroValues[2])

        // Normalize the rotation vector if it's big enough to get the axis
        if (omegaMagnitude > EPSILON) {
            normValues[0] = gyroValues[0] / omegaMagnitude
            normValues[1] = gyroValues[1] / omegaMagnitude
            normValues[2] = gyroValues[2] / omegaMagnitude
        }

        // Integrate around this axis with the angular speed by the timestep
        // in order to get a delta rotation from this sample over the timestep
        // We will convert this axis-angle representation of the delta rotation
        // into a quaternion before turning it into the rotation matrix.
        var thetaOverTwo = omegaMagnitude * timeFactor
        var sinThetaOverTwo = kotlin.math.sin(thetaOverTwo)
        var cosThetaOverTwo = kotlin.math.cos(thetaOverTwo)
        deltaRotationVector[0] = sinThetaOverTwo * normValues[0]
        deltaRotationVector[1] = sinThetaOverTwo * normValues[1]
        deltaRotationVector[2] = sinThetaOverTwo * normValues[2]
        deltaRotationVector[3] = cosThetaOverTwo
    }

    private fun gyroFunction(event: SensorEvent) {
        if (accMagOrientation.isEmpty()) {
            return
        }

        if (initState) {
            var initMatrix = getRotationMatrixFromOrientation(accMagOrientation)
            var test = FloatArray(3)
            SensorManager.getOrientation(initMatrix, test)
            gyroMatrix = matrixMultiplication(gyroMatrix, initMatrix)
            initState = false
        }

        var deltaVector = FloatArray(4)
        if (timestamp != 0f) {
            var dT = (event.timestamp - timestamp) * NS2S
            System.arraycopy(event.values, 0, gyro, 0, 3)
            getRotationVectorFromGyro(gyro, deltaVector, dT / 2.0f)
        }

        timestamp = event.timestamp.toFloat()

        var deltaMatrix = FloatArray(9)
        SensorManager.getRotationMatrixFromVector(deltaMatrix, deltaVector)

        gyroMatrix = matrixMultiplication(gyroMatrix, deltaMatrix)

        SensorManager.getOrientation(gyroMatrix, gyroOrientation)

        Log.d("VALOR OTIENTATION GYRO",gyroOrientation[1].toString())
    }


    private fun getRotationMatrixFromOrientation(o: FloatArray): FloatArray {
        var xM = FloatArray(9)
        var yM = FloatArray(9)
        var zM = FloatArray(9)

        var sinX = sin(o[1].toDouble()).toFloat()
        var cosX = cos(o[1].toDouble()).toFloat()
        var sinY = sin(o[2].toDouble()).toFloat()
        var cosY = cos(o[2].toDouble()).toFloat()
        var sinZ = sin(o[0].toDouble()).toFloat()
        var cosZ = cos(o[0].toDouble()).toFloat()

        // Rotation about x-axis (pitch)
        xM[0] = 1.0f
        xM[1] = 0.0f
        xM[2] = 0.0f
        xM[3] = 0.0f
        xM[4] = cosX
        xM[5] = sinX
        xM[6] = 0.0f
        xM[7] = -sinX
        xM[8] = cosX

        // Rotation about y-axis (roll)
        yM[0] = cosY
        yM[1] = 0.0f
        yM[2] = sinY
        yM[3] = 0.0f
        yM[4] = 1.0f
        yM[5] = 0.0f
        yM[6] = -sinY
        yM[7] = 0.0f
        yM[8] = cosY

        // Rotation about z-axis (azimuth)
        zM[0] = cosZ
        zM[1] = sinZ
        zM[2] = 0.0f
        zM[3] = -sinZ
        zM[4] = cosZ
        zM[5] = 0.0f
        zM[6] = 0.0f
        zM[7] = 0.0f
        zM[8] = 1.0f

        // Rotation order is y, x, z (roll, pitch, azimuth)
        var resultMatrix = matrixMultiplication(xM, yM)
        resultMatrix = matrixMultiplication(zM, resultMatrix)
        return resultMatrix
    }

    private fun matrixMultiplication(A: FloatArray, B: FloatArray): FloatArray {
        var result = FloatArray(9)

        result[0] = A[0] * B[0] + A[1] * B[3] + A[2] * B[6]
        result[1] = A[0] * B[1] + A[1] * B[4] + A[2] * B[7]
        result[2] = A[0] * B[2] + A[1] * B[5] + A[2] * B[8]

        result[3] = A[3] * B[0] + A[4] * B[3] + A[5] * B[6]
        result[4] = A[3] * B[1] + A[4] * B[4] + A[5] * B[7]
        result[5] = A[3] * B[2] + A[4] * B[5] + A[5] * B[8]

        result[6] = A[6] * B[0] + A[7] * B[3] + A[8] * B[6]
        result[7] = A[6] * B[1] + A[7] * B[4] + A[8] * B[7]
        result[8] = A[6] * B[2] + A[7] * B[5] + A[8] * B[8]

        return result
    }




    fun onResume() {
        fuseTimer.scheduleAtFixedRate(
            calculateFusedOrientationTask(),
            1000,
            TIME_CONSTANT.toLong()
        )
    }

    fun onPause() {
        fuseTimer.cancel()
    }

    inner class calculateFusedOrientationTask : TimerTask() {
        override fun run() {
            val oneMinusCoeff = 1.0f - FILTER_COEFFICIENT
            fusedOrientation[0] =
                FILTER_COEFFICIENT * gyroOrientation[0] + oneMinusCoeff * accMagOrientation[0]

            fusedOrientation[1] =
                FILTER_COEFFICIENT * gyroOrientation[1] + oneMinusCoeff * accMagOrientation[1]

            fusedOrientation[2] =
                FILTER_COEFFICIENT * gyroOrientation[2] + oneMinusCoeff * accMagOrientation[2]

            gyroMatrix = getRotationMatrixFromOrientation(fusedOrientation)
            System.arraycopy(fusedOrientation, 0, gyroOrientation, 0, 3)

            Log.d("VALOR OTIENTATION FUSE",fusedOrientation[1].toString())
        }

    }

    fun getPitchValue(calibracion: Float): Float {
        var azimuth: Float = fusedOrientation[0]
        var pitch: Float = fusedOrientation[1]
        var roll: Float = fusedOrientation[2]


        if (abs(pitch) < valueDrift) {
            pitch = 0f
        }

        var pitchValue = Math.toDegrees(pitch.toDouble()).toFloat() + 90f - calibracion

        Log.d("VALOR LOCo", pitchValue.toString())

        return pitchValue // Este es solo un valor de ejemplo, debes reemplazarlo con tu lógica real de cálculo de ángulo
    }
}
