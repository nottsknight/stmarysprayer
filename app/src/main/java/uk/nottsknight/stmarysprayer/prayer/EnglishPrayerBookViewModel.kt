package uk.nottsknight.stmarysprayer.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import uk.nottsknight.stmarysprayer.esvapi.EsvApiController

@ExperimentalSerializationApi
class EnglishPrayerBookViewModel(app: Application) : AndroidViewModel(app) {
    private val api: EsvApiController by lazy { EsvApiController(app.baseContext) }

    val scriptureSentence1 = liveData(Dispatchers.IO) {
        when (val passages = api.getPassages(listOf(SCRIPTURE_SENTENCES.random()))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> Unit
        }
    }

    val scriptureSentence2 = liveData(Dispatchers.IO) {
        when (val passages = api.getPassages(listOf(SCRIPTURE_SENTENCES.random()))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> Unit
        }
    }

    val psalm = liveData(Dispatchers.IO) {
        when (val passages = api.getPassages(listOf("Psalm 95"))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> Unit
        }
    }

    val oldTestament = liveData(Dispatchers.IO) {
        when (val passages = api.getPassages(listOf("Genesis 1"))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> Unit
        }
    }

    val newTestament = liveData(Dispatchers.IO) {
        when (val passages = api.getPassages(listOf("Matthew 1"))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> Unit
        }
    }

    companion object {
        private val SCRIPTURE_SENTENCES = arrayOf(
            "Psalm 46:1",
            "Psalm 51:3",
            "Psalm 51:9",
            "Psalm 51:17",
            "Psalm 118:24",
            "Psalm 143:2",
            "Isaiah 55:6",
            "Jeremiah 10:24",
            "Ezekiel 18:27",
            "Daniel 9:9",
            "Joel 2:13",
            "Habakkuk 2:20",
            "Matthew 3:2",
            "Luke 15:18",
            "John 4:23",
            "Philippians 4:6",
            "Hebrews 13:15",
            "1 John 1:8-9",
            "Revelation 4:11"
        )
    }
}