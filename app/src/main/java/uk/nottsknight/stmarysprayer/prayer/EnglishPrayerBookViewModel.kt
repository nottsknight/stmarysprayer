package uk.nottsknight.stmarysprayer.prayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnglishPrayerBookViewModel : ViewModel() {
    val scriptureSentence1 = MutableLiveData<String>()
    val scriptureSentence2 = MutableLiveData<String>()
    val psalm = MutableLiveData<String>()
    val oldTestament = MutableLiveData<String>()
    val newTestament = MutableLiveData<String>()
}