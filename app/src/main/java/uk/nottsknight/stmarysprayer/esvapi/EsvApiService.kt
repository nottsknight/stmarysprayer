package uk.nottsknight.stmarysprayer.esvapi

import arrow.core.Either
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "8c26a895d2ac3ef9b216ce33534e7566af088bf4"
private const val ROOT_URI = "https://api.esv.org/v3/passage/text"

private interface EsvApiService {
    @Headers("Authorization: Token $API_KEY")
    @GET
    fun getPassages(@Query("q") passages: String): Call<EsvApiResponse>
}

@ExperimentalSerializationApi
class EsvApiController {
    private val service: EsvApiService
    private val whitespaceRegex = Regex("\\s*")

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
        service = retrofit.create(EsvApiService::class.java)
    }

    fun getPassages(passages: List<String>): Either<Throwable, List<String>> {
        val query =
            passages.map { it.replace(whitespaceRegex, "") }.reduce { acc, s -> acc + s }

        service.getPassages(query).enqueue(object : Callback<EsvApiResponse> {
            override fun onResponse(
                call: Call<EsvApiResponse>,
                response: Response<EsvApiResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<EsvApiResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return Either.Left(NotImplementedError())
    }
}