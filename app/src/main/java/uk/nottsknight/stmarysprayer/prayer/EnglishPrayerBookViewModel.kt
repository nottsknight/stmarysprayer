package uk.nottsknight.stmarysprayer.prayer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import uk.nottsknight.stmarysprayer.R
import uk.nottsknight.stmarysprayer.esvapi.EsvApiController
import java.util.*

@ExperimentalSerializationApi
class EnglishPrayerBookViewModel(app: Application) : AndroidViewModel(app) {
    private val api: EsvApiController = EsvApiController(app.baseContext)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private fun makeErrorMessage(code: Int) = "Failed to download passage (error $code)"

    val scriptureSentence1 = liveData(Dispatchers.IO) {
        Log.d("smwp", "scriptureSentence1 liveData")
        when (val passages = api.getPassages(listOf(SCRIPTURE_SENTENCES.random()))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> _errorMessage.postValue(makeErrorMessage(passages.value))
        }
    }

    val scriptureSentence2 = liveData(Dispatchers.IO) {
        Log.d("smwp", "scriptureSentence2 liveData")
        when (val passages = api.getPassages(listOf(SCRIPTURE_SENTENCES.random()))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> _errorMessage.postValue(makeErrorMessage(passages.value))
        }
    }

    val confession = liveData {
        Log.d("smwp", "confession liveData")
        val confession = app.resources.getStringArray(R.array.epb_confession_texts).random()
        emit(confession)
    }

    val psalm = liveData(Dispatchers.IO) {
        Log.d("smwp", "psalm liveData")
        when (val passages = api.getPassages(listOf("Psalm 95"))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> _errorMessage.postValue(makeErrorMessage(passages.value))
        }
    }

    val oldTestament = liveData(Dispatchers.IO) {
        Log.d("smwp", "oldTestament liveData")
        when (val passages = api.getPassages(listOf("Genesis 1"))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> _errorMessage.postValue(makeErrorMessage(passages.value))
        }
    }

    val newTestament = liveData(Dispatchers.IO) {
        Log.d("smwp", "newTestament liveData")
        when (val passages = api.getPassages(listOf("Matthew 1"))) {
            is Either.Right -> emit(passages.value[0])
            is Either.Left -> _errorMessage.postValue(makeErrorMessage(passages.value))
        }
    }

    val collect = liveData(Dispatchers.IO) {
        val collectArray = when (Calendar.getInstance()[Calendar.AM_PM]) {
            Calendar.AM -> R.array.epb_morning_collects
            Calendar.PM -> R.array.epb_evening_collects
            else -> 0
        }

        if (collectArray != 0) {
            app.resources.getStringArray(collectArray).random().let { emit(it) }
        } else {
            _errorMessage.postValue("Could not determine time of day")
        }
    }

    val blessing = liveData(Dispatchers.IO) {
        app.resources.getStringArray(R.array.epb_conclusion_blessings).random().let { emit(it) }
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