package uk.nottsknight.stmarysprayer

import androidx.lifecycle.ViewModel

class ServiceSelectViewModel : ViewModel() {
    var englishPrayerBookCallback: NavigationCallback? = null
    var commonWorshipCallback: NavigationCallback? = null

    fun onClickEnglishPrayerBook() = englishPrayerBookCallback?.performNavigation()

    fun onClickCommonWorshipDailyPrayer() = commonWorshipCallback?.performNavigation()
}