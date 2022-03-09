package com.mutualmobile.barricade2

import com.mutualmobile.barricade2.response.BarricadeResponse
import com.mutualmobile.barricade2.response.BarricadeResponseSet

/**
 * Contract for a Barricade configuration
 */
interface IBarricadeConfig {
    fun getConfigs(): HashMap<String, BarricadeResponseSet>
    fun getResponseForEndpoint(endpoint: String): BarricadeResponse?
}
