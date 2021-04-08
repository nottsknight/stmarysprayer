package uk.nottsknight.stmarysprayer.esvapi

import android.content.Context
import androidx.preference.PreferenceManager
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import uk.nottsknight.stmarysprayer.R

object EsvApi {
    /**
     * Request all the given [passages] from the ESV API.
     */
    fun requestPassages(
        /**
         * Application context for Volley and the PreferenceManager to use.
         */
        context: Context,
        /**
         * List of Scripture references to get.
         */
        passages: List<String>,
        /**
         * Error response callback, defaults to noop.
         */
        onError: Response.ErrorListener = Response.ErrorListener {},
        /**
         * Success response callback.
         */
        onSuccess: Response.Listener<EsvApiResponse>
    ) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val params = listOf(
            R.string.prefs_scripture_footnotes_key,
            R.string.prefs_scripture_headings_key,
            R.string.prefs_scripture_versenumbers_key,
            R.string.prefs_scripture_firstversenumbers_key
        ).map {
            context.getString(it)
        }.map {
            it to preferences.getBoolean(it, false)
        }

        val requestQueue = Volley.newRequestQueue(context)
        val request = EsvApiRequest(passages, params, onSuccess, onError)
        requestQueue.add(request)
    }
}
