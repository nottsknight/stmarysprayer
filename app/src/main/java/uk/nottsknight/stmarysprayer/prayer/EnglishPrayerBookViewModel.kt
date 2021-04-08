package uk.nottsknight.stmarysprayer.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uk.nottsknight.stmarysprayer.R

class EnglishPrayerBookViewModel(private val app: Application) : AndroidViewModel(app) {
    val scriptureSentence1 = MutableLiveData<String>()
    val scriptureSentence2 = MutableLiveData<String>()
    val confession = MutableLiveData<String>()
    val psalm = MutableLiveData<String>()
    val oldTestament = MutableLiveData<String>()
    val newTestament = MutableLiveData<String>()

    fun initialiseTexts() {
        app.resources.getStringArray(R.array.epb_confession_texts).random()
            .let { confession.postValue(it) }
    }
}