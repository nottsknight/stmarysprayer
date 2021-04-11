package uk.nottsknight.stmarysprayer.esvapi

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import arrow.core.Either
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uk.nottsknight.stmarysprayer.R

private const val API_KEY = "8c26a895d2ac3ef9b216ce33534e7566af088bf4"
private const val ROOT_URI = "https://api.esv.org/v3/passage/"

private interface EsvApiService {
    @Headers("Authorization: Token $API_KEY")
    @GET("text")
    suspend fun getPassagesText(
        @Query("q") passages: String,
        @Query("include-verse-numbers") includeVerseNumbers: Boolean,
        @Query("include-first-verse-numbers") includeFirstVerseNumbers: Boolean,
        @Query("include-headings") includeHeadings: Boolean,
        @Query("include-footnotes") includeFootnotes: Boolean
    ): Response<EsvApiResponse>
}

@ExperimentalSerializationApi
class EsvApiController(private val context: Context) {
    private val whitespaceRegex = Regex("\\s*")
    private val service: EsvApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ROOT_URI)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
        retrofit.create(EsvApiService::class.java)
    }

    suspend fun getPassages(passages: List<String>): Either<Int, List<String>> =
        withContext(Dispatchers.IO) {
            val query = passages
                .map { it.replace(whitespaceRegex, "") }
                .reduce { acc, s -> acc + s }

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val verseNumbers = preferences.getBoolean(
                context.getString(R.string.prefs_scripture_versenumbers_key),
                true
            )
            val firstVerseNumbers = preferences.getBoolean(
                context.getString(R.string.prefs_scripture_firstversenumbers_key),
                true
            )
            val headings = preferences.getBoolean(
                context.getString(R.string.prefs_scripture_headings_key),
                true
            )
            val footnotes = preferences.getBoolean(
                context.getString(R.string.prefs_scripture_footnotes_key),
                true
            )

            val response =
                service.getPassagesText(query, verseNumbers, firstVerseNumbers, headings, footnotes)
            if (response.isSuccessful) {
                Either.Right(response.body()?.passages?.asList() ?: listOf())
            } else {
                Either.Left(response.code())
            }
        }
}
