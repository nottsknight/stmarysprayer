package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class LectionaryTable(
    @PrimaryKey val uid: Int,
    val name: String,
    val abbreviation: String
)

@Entity
data class LiturgicalSeason(
    @PrimaryKey val uid: Int,
    val name: String
)

@Entity
data class LiturgicalDate(
    @PrimaryKey val uid: Int,
    val seasonId: Int,
    val week: Int,
    val day: Int
)

@Entity(indices = [Index(value = ["lectionaryId", "dateId"], unique = true)])
data class Readings(
    @PrimaryKey val uid: Int,
    val lectionaryId: Int,
    val dateId: Int,
    val oldTestament: String,
    val psalm: String,
    val newTestament: String,
    val gospel: String?
)
