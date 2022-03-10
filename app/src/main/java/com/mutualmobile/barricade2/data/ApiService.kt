package com.mutualmobile.barricade2.data

import com.mutualmobile.barricade2.annotation.Barricade
import com.mutualmobile.barricade2.data.models.JokeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/jokes/random")
    @Barricade(
        responses = [
            com.mutualmobile.barricade2.annotation.Response(
                fileName = "success.json",
                isDefault = true
            )
        ]
    )
    suspend fun getRandomJoke(): Response<JokeResponse>
}
