package uk.nottsknight.stmarysprayer.lectio

import java.io.Serializable
import java.util.*
import kotlin.math.floor
import kotlin.properties.Delegates

class LiturgicalCalendar private constructor() : Serializable, Comparable<LiturgicalCalendar> {
    lateinit var season: Season
    var week: Int by Delegates.notNull()

    override fun compareTo(other: LiturgicalCalendar) =
        if (season == other.season) week - other.week else season.compareTo(other.season)

    private fun Int.toOrdinal() = when (this) {
        1, 21, 31 -> "${this}st"
        2, 22 -> "${this}nd"
        3, 23 -> "${this}rd"
        else -> "${this}th"
    }

    override fun toString(): String = when (season.isOrdinaryTime) {
        true -> "${week.toOrdinal()} Sunday ${season.label}"
        false -> "${week.toOrdinal()} Sunday of ${season.label}"
    }

    enum class Season(val label: String, val length: Int, val isOrdinaryTime: Boolean) {
        ADVENT("Advent", 4, false),
        CHRISTMAS("Christmas", 2, false),
        EPIPHANY("Epiphany", 4, false),
        BEFORE_LENT("before Lent", 5, true),
        LENT("Lent", 6, false),
        EASTER("Easter", 7, false),
        AFTER_TRINITY("after Trinity", 23, true),
        BEFORE_ADVENT("before Advent", 4, true);
    }

    companion object {
        fun forInstance(cal: Calendar): LiturgicalCalendar {
            return LiturgicalCalendar()
        }

        /**
         * Obtain a new [Calendar] instance set to the date of Easter for the given year.
         * The date is calculated according to Gauss' algorithm.
         */
        fun dateOfEaster(year: Int): Calendar {
            val a = year % 19
            val b = year % 4
            val c = year % 7
            val k = floor(year / 100.0)
            val p = floor((13 + 8 * k) / 25.0)
            val q = floor(k / 4.0)
            val m = (15 - p + k - q) % 30
            val n = (4 + k - q) % 7
            val d = (19 * a + m) % 30
            val e = (2 * b + 4 * c + 6 * d + n) % 7

            var day = 22 + d + e
            val month = if (day > 31) Calendar.APRIL else Calendar.MARCH
            if (day > 31) day = d + e - 9
            return GregorianCalendar(year, month, day.toInt())
        }
    }
}