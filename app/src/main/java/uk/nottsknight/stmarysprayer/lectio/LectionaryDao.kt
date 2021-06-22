package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LectionaryDao {
    @Query("SELECT * FROM Lectionary")
    fun selectLectionaryTables(): List<Lectionary>

    @Query("SELECT * FROM LiturgicalSeason")
    fun selectLiturgicalSeasons(): List<LiturgicalSeason>

    @Query("SELECT * FROM LectionaryEntry WHERE lectionaryId = :table AND seasonId = :season AND week = :week and day = :day")
    fun selectReadingForDate(table: Int, season: Int, week: Int, day: Int): LectionaryEntry?

    @Insert
    fun insertScripture(scripture: Scripture)

    @Delete
    fun deleteScripture(scripture: Scripture)

    @Query("SELECT * FROM Scripture WHERE reference LIKE :reference")
    fun selectScripture(reference: String): Scripture?
}
