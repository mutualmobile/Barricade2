package com.mutualmobile.barricade2.annotation

/**
 * Annotation to declare a response for an endpoint. Each endpoint will typically have many of
 * these. It has sensible defaults for most params.
 */
annotation class Response(
    val fileName: String,
    val statusCode: Int,
    val type: String = "application/json",
    val isDefault: Boolean = false
)
