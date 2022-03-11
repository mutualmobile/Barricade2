package com.mutualmobile.barricadeSample

import android.app.Application
import com.mutualmobile.barricade2.Barricade
import com.mutualmobile.barricade2.BarricadeConfig

class B2Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Barricade.Builder(this, BarricadeConfig.getInstance()).install()
    }
}
