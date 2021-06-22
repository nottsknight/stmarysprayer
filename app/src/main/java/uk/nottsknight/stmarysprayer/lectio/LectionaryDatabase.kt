package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [Lectionary::class, LiturgicalSeason::class, LectionaryEntry::class, Scripture::class],
    version = 1
)
@TypeConverters(DateTimeConverters::class)
abstract class LectionaryDatabase : RoomDatabase() {
    abstract fun dao(): LectionaryDao
}

object DateTimeConverters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun stringToDateTime(input: String?): ZonedDateTime? =
        input?.let { formatter.parse(input, ZonedDateTime::from) }

    @TypeConverter
    @JvmStatic
    fun dateTimeToString(date: ZonedDateTime?): String? = date?.format(formatter)
}
