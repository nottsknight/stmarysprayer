package uk.nottsknight.stmarysprayer.esvapi

import arrow.core.Either
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.serialization.responseObject

object EsvApi {
    private const val API_KEY = "8c26a895d2ac3ef9b216ce33534e7566af088bf4"
    private const val ROOT_URI = "https://api.esv.org/v3/passage/text"

    private val WHITESPACE = Regex("\\s")

    fun requestPassages(queries: List<String>): Either<String, List<String>> {
        // build query
        val queryString =
            queries.map { it.replace(WHITESPACE, "") }.reduce { acc, s -> acc + s }
        val params = listOf("q" to queryString)

        // make request
        val (_, response, result) = Fuel.get(ROOT_URI, params)
            .set("Authorization", "Token $API_KEY").responseObject<EsvApiResponse>()

        // process response
        return if (response.isSuccessful) {
            Either.Right(result.get().passages.toList())
        } else {
            Either.Left(result.component2()?.toString() ?: "An unspecified error occurred")
        }
    }
}
