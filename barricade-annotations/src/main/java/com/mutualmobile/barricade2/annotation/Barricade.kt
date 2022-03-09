package com.mutualmobile.barricade2.annotation

/**
 * Annotation to configure Barricade for an endpoint.
 */
@Retention
@Target(AnnotationTarget.CLASS)
annotation class Barricade(
    val endpoint: String = "",
    val responses: Array<Response> = []
)
