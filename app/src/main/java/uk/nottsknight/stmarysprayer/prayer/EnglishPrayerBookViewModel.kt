package uk.nottsknight.stmarysprayer.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.nottsknight.stmarysprayer.R
import uk.nottsknight.stmarysprayer.esvapi.EsvApi

class EnglishPrayerBookViewModel(private val app: Application) : AndroidViewModel(app) {
    val scriptureSentence1 = MutableLiveData<String>()
    val scriptureSentence2 = MutableLiveData<String>()
    val confession = MutableLiveData<String>()
    val psalm = MutableLiveData<String>()
    val oldTestament = MutableLiveData<String>()
    val newTestament = MutableLiveData<String>()

    fun initialiseTexts() = viewModelScope.launch {
        val passages = listOf("Psalm 46:1", "Psalm 51:3", "Psalm 95", "Genesis 1", "Matthew 1")
        EsvApi.requestPassages(app, passages) { response ->
            scriptureSentence1.postValue(response.passages[0])
            scriptureSentence2.postValue(response.passages[1])
            psalm.postValue(response.passages[2])
            oldTestament.postValue(response.passages[3])
            newTestament.postValue(response.passages[4])
        }

        app.resources.getStringArray(R.array.epb_confession_texts).random()
            .let { confession.postValue(it) }
    }
}