package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LectionaryTable::class,
        LiturgicalSeason::class,
        LiturgicalDate::class,
        Readings::class],
    version = 1
)
abstract class LectionaryDatabase : RoomDatabase() {
    abstract fun dao(): LectionaryDao
}
