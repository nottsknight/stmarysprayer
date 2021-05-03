package uk.nottsknight.stmarysprayer.lectio

import arrow.core.Either
import java.io.Serializable
import java.util.*
import kotlin.math.floor

class LiturgicalCalendar private constructor(
    var season: Season,
    var week: Int,
    var day: Int? = null
) : Serializable, Comparable<LiturgicalCalendar> {

    override fun compareTo(other: LiturgicalCalendar) =
        if (season == other.season) week - other.week else season.compareTo(other.season)

    private fun Int.toOrdinal() = when (this) {
        1, 21, 31 -> "${this}st"
        2, 22 -> "${this}nd"
        3, 23 -> "${this}rd"
        else -> "${this}th"
    }

    override fun toString(): String =
        "${week.toOrdinal()} ${if (season.isOrdinaryTime) "Sunday" else "Sunday of"} ${season.label}"

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
            val advent = startOfAdvent(cal[Calendar.YEAR])
            if (cal < advent) advent[Calendar.YEAR] -= 1
            return LiturgicalCalendar(Season.ADVENT, 1)
        }

        /**
         * Obtain a new [Calendar] instance set to the first Sunday of Advent for
         * the given year.
         */
        fun startOfAdvent(year: Int): Calendar {
            val cal = GregorianCalendar(year, Calendar.DECEMBER, 25)
            while (cal[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY) cal[Calendar.DATE] -= 1
            cal[Calendar.WEEK_OF_YEAR] -= 3
            return cal
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

        private val LITURGICAL_DATE_PATTERN =
            Regex("""(?<month>[0-7])-(?<week>[01][0-9]|2[0-3])(?:-(?<day>[0-6]))?""")

        fun parse(input: String): Either<IllegalArgumentException, LiturgicalCalendar> {
            val parsedInput = LITURGICAL_DATE_PATTERN.matchEntire(input)
                ?: return Either.Left(IllegalArgumentException())

            val (month, week, day) = parsedInput.groups.let {
                val month = it[1]?.value?.toInt()
                val week = it[2]?.value?.toInt()
                val day = it[3]?.value?.toInt()
                Triple(month, week, day)
            }
            if (month == null || week == null) return Either.Left(IllegalArgumentException())

            val season = when (month) {
                0 -> Season.ADVENT
                1 -> Season.CHRISTMAS
                2 -> Season.EPIPHANY
                3 -> Season.BEFORE_LENT
                4 -> Season.LENT
                5 -> Season.EASTER
                6 -> Season.AFTER_TRINITY
                7 -> Season.BEFORE_ADVENT
                else -> null
            } ?: return Either.Left(IllegalArgumentException())

            return Either.Right(LiturgicalCalendar(season, week, day))
        }
    }
}
