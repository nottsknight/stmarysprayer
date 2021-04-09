package uk.nottsknight.stmarysprayer.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import uk.nottsknight.stmarysprayer.esvapi.EsvApiController

class EnglishPrayerBookViewModel(app: Application) : AndroidViewModel(app) {
    @ExperimentalSerializationApi
    private val api: EsvApiController by lazy { EsvApiController(app.baseContext) }
}