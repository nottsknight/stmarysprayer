package uk.nottsknight.stmarysprayer.esvapi

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.Volley

object EsvApi {
    /**
     * Request all the given [passages] from the ESV API.
     */
    fun requestPassages(
        /**
         * Application context for Volley to use.
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

        val requestQueue = Volley.newRequestQueue(context)
        val request = EsvApiRequest(passages, listOf(), onSuccess, onError)
        requestQueue.add(request)
    }
}
