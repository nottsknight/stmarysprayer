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

    enum class Season {
        ADVENT, CHRISTMAS, EPIPHANY, BEFORE_LENT, LENT, EASTER, AFTER_TRINITY, BEFORE_ADVENT
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

            val month = if (22 + d + e > 31) Calendar.APRIL else Calendar.MARCH
            val day = if (22 + d + e > 31) d + e - 9 else 22 + d + e
            return GregorianCalendar(year, month, day.toInt())
        }
    }
}