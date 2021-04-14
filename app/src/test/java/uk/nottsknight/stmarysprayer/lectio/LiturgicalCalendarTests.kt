package uk.nottsknight.stmarysprayer.lectio

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.*

class LiturgicalCalendarTests {

    @Test
    fun testDateOfEaster() {
        val date1 = LiturgicalCalendar.dateOfEaster(2008)
        assertThat(date1[Calendar.YEAR]).isEqualTo(2008)
        assertThat(date1[Calendar.MONTH]).isEqualTo(Calendar.MARCH)
        assertThat(date1[Calendar.DAY_OF_MONTH]).isEqualTo(23)

        val date2 = LiturgicalCalendar.dateOfEaster(2009)
        assertThat(date2[Calendar.YEAR]).isEqualTo(2009)
        assertThat(date2[Calendar.MONTH]).isEqualTo(Calendar.APRIL)
        assertThat(date2[Calendar.DAY_OF_MONTH]).isEqualTo(12)

        val date3 = LiturgicalCalendar.dateOfEaster(2010)
        assertThat(date3[Calendar.YEAR]).isEqualTo(2010)
        assertThat(date3[Calendar.MONTH]).isEqualTo(Calendar.APRIL)
        assertThat(date3[Calendar.DAY_OF_MONTH]).isEqualTo(4)

        val date4 = LiturgicalCalendar.dateOfEaster(2013)
        assertThat(date4[Calendar.YEAR]).isEqualTo(2013)
        assertThat(date4[Calendar.MONTH]).isEqualTo(Calendar.MARCH)
        assertThat(date4[Calendar.DAY_OF_MONTH]).isEqualTo(31)
    }
}
