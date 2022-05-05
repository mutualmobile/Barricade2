package com.mutualmobile.barricade.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import com.mutualmobile.barricade.Barricade
import com.mutualmobile.barricade.BarricadeConfigActivity
import kotlin.math.abs

private const val SHAKE_THRESHOLD = 1200
private var lastUpdate: Long = 0
private var lastX = 0f
private var lastY = 0f
private var lastZ = 0f
private var shakeCount = 0

class BarricadeShakeListener(
    private val application: Application,
    private val sensorManager: SensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
) : SensorEventListener, Application.ActivityLifecycleCallbacks {
    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val curTime = System.currentTimeMillis()
        val diffTime = curTime.minus(lastUpdate)
        if (diffTime > 100) {
            lastUpdate = curTime
            val x = sensorEvent?.values?.get(0)
            val y = sensorEvent?.values?.get(1)
            val z = sensorEvent?.values?.get(2)
            x?.let { nnX ->
                y?.let { nnY ->
                    z?.let { nnZ ->
                        val speed = abs(nnX + nnY + nnZ - lastX - lastY - lastZ) / diffTime * 10000
                        shakeCount = if (speed > SHAKE_THRESHOLD) ++shakeCount else 0
                        if (shakeCount >= 2) {
                            shakeCount = 0
                            Barricade.getInstance().launchConfigActivity(application)
                        }
                        lastX = nnX
                        lastY = nnY
                        lastZ = nnZ
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        toggleShakeListener(enableListener = activity !is BarricadeConfigActivity)
    }

    private fun toggleShakeListener(enableListener: Boolean) {
        if (enableListener) {
            sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
            )
        } else {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }
}
