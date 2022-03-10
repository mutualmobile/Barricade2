package com.mutualmobile.barricade2

import android.app.Application
import com.mutualmobile.barricade2.annotation.Barricade
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

class B2Application : Application() {
    override fun onCreate() {
        super.onCreate()
        com.mutualmobile.barricade2.Barricade.Builder(this, BarricadeConfig.getInstance()).install()
    }
}

interface ApiService {
    @GET("")
    @Barricade(
        endpoint = "www.google.com",
        responses = [
            com.mutualmobile.barricade2.annotation.Response(
                fileName = "success.json",
                isDefault = true
            ),
            com.mutualmobile.barricade2.annotation.Response(fileName = "failure.json"),
        ]
    )
    fun MyNetworkCall(): Response<ResponseBody>

    @GET("")
    @Barricade(
        endpoint = "www.google1.com",
        responses = [
            com.mutualmobile.barricade2.annotation.Response(
                fileName = "failure.json",
                isDefault = true
            )
        ]
    )
    fun MyNewNetworkCall(): Response<ResponseBody>
}
