package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Dao
import androidx.room.Query

@Dao
interface LectionaryDao {
    @Query("SELECT * FROM LectionaryTable")
    suspend fun selectAllLectionaryTables(): Array<LectionaryTable>

    @Query("SELECT * FROM LectionaryTable WHERE uid = :id")
    suspend fun selectLetionaryTable(id: Int): LectionaryTable

    @Query("SELECT * FROM LiturgicalDate WHERE seasonId = :season AND week = :week AND day = :day")
    suspend fun selectDateForValues(season: Int, week: Int, day: Int): LiturgicalDate

    @Query("SELECT * FROM Readings WHERE lectionaryId = :table AND dateId = :date")
    suspend fun selectReadingsForTableAndDate(table: Int, date: Int): Readings?
}
