package uk.nottsknight.stmarysprayer.esvapi

import android.content.Context
import androidx.preference.PreferenceManager
import arrow.core.Either
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
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
    fun getPassagesText(
        @Query("q") passages: String,
        @Query("include-verse-numbers") includeVerseNumbers: Boolean,
        @Query("include-first-verse-numbers") includeFirstVerseNumbers: Boolean,
        @Query("include-headings") includeHeadings: Boolean,
        @Query("include-footnotes") includeFootnotes: Boolean
    ): Call<EsvApiResponse>
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

    fun getPassages(passages: List<String>): Either<Int, List<String>> {
        val query =
            passages.map { it.replace(whitespaceRegex, "") }.reduce { acc, s -> acc + s }

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

        return service.getPassagesText(query, verseNumbers, firstVerseNumbers, headings, footnotes)
            .execute().let {
                if (it.isSuccessful) {
                    Either.Right(it.body()?.passages?.toList() ?: listOf())
                } else {
                    Either.Left(it.code())
                }
            }
    }
}
