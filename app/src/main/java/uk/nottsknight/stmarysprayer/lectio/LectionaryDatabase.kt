package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Lectionary::class, LiturgicalSeason::class, LectionaryEntry::class],
    version = 1
)
abstract class LectionaryDatabase : RoomDatabase() {
    abstract fun dao(): LectionaryDao
}
