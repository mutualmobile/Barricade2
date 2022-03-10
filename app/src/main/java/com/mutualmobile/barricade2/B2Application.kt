package com.mutualmobile.barricade2

import android.app.Application

class B2Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Barricade.Builder(this, BarricadeConfig.getInstance()).install()
    }
}
