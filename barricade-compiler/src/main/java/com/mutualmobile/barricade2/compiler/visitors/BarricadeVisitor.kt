package com.mutualmobile.barricade2.compiler.visitors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.mutualmobile.barricade2.annotation.Barricade
import com.mutualmobile.barricade2.annotation.Response
import com.mutualmobile.barricade2.compiler.BarricadeProcessor
import com.mutualmobile.barricade2.compiler.utils.writeToFile
import java.io.OutputStream

private const val TAG = "BarricadeVisitor"

class BarricadeVisitor(
    private val file: OutputStream,
    private val logger: KSPLogger,
    private val annotationName: String? = Barricade::class.simpleName
) : KSVisitorVoid() {
    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        val annotation = function.annotations.first { annotation ->
            annotation.shortName.asString() == annotationName
        }

        val endpoint = annotation.arguments.first { argument ->
            argument.name?.asString() == Barricade::endpoint.name
        }.value as String

        val responsesArgument = annotation.arguments.first { argument ->
            argument.name?.asString() == Barricade::responses.name
        }.value as ArrayList<*>

        val responses = responsesArgument.map {
            (it as KSAnnotation).arguments
        }

        responses.forEach { response ->
            val fileName = response.first { it.name?.asString() == Response::fileName.name }.value as String
            val isDefault = response.first { it.name?.asString() == Response::isDefault.name }.value as Boolean
            val statusCode = response.first { it.name?.asString() == Response::statusCode.name }.value as Int
            val type = response.first { it.name?.asString() == Response::type.name }.value as String
            file writeToFile "\t\tbarricadeResponsesForRandom.add(${BarricadeProcessor.PACKAGE_NAME}.response.BarricadeResponse($statusCode, \"$fileName\", \"$type\"))\n"
            logger.info("Found args: $fileName, $isDefault, $statusCode, $type")
        }
    }
}
