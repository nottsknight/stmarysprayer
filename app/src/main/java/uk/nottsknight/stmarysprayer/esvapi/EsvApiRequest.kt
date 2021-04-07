package uk.nottsknight.stmarysprayer.esvapi

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

private const val API_KEY = "8c26a895d2ac3ef9b216ce33534e7566af088bf4"
private const val ROOT_URI = "https://api.esv.org/v3/passage/text"

private val WHITESPACE = Regex("\\s")

private fun String.buildUriFrom(
    passages: List<String>,
    parameters: List<Pair<String, Any>>
): String {

    val queries = passages.map { it.replace(WHITESPACE, "") }.reduce { acc, s -> acc + s }
    var uri = "$this?q=$queries"
    for ((k, v) in parameters) {
        uri += "&$k=$v"
    }
    return uri
}

class EsvApiRequest(
    passages: List<String>,
    parameters: List<Pair<String, Any>>,
    private val successListener: Response.Listener<EsvApiResponse>,
    errorListener: Response.ErrorListener
) : Request<EsvApiResponse>(
    Method.GET,
    ROOT_URI.buildUriFrom(passages, parameters),
    errorListener
) {

    init {
        require(passages.isNotEmpty())
    }

    override fun getHeaders(): MutableMap<String, String> =
        mutableMapOf("Authorization" to "Token $API_KEY")

    override fun parseNetworkResponse(response: NetworkResponse?): Response<EsvApiResponse> {
        return try {
            val charset = HttpHeaderParser.parseCharset(response?.headers)
            val json = String(response?.data ?: ByteArray(0), Charset.forName(charset))
            val esvResponse = Json.decodeFromString<EsvApiResponse>(json)
            Response.success(esvResponse, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: SerializationException) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: EsvApiResponse?) = successListener.onResponse(response)
}