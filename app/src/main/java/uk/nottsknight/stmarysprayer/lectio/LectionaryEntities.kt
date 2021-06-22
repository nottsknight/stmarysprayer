package uk.nottsknight.stmarysprayer.lectio

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Lectionary(
    @PrimaryKey val uid: Int,
    val tableName: String,
    val subtableName: String?
)

@Entity
data class LiturgicalSeason(
    @PrimaryKey val uid: Int,
    val name: String
)

@Entity
data class LectionaryEntry(
    @PrimaryKey val uid: Int,
    val lectionaryId: Int,
    val seasonId: Int,
    val week: Int,
    val day: Int,
    val oldTestament: String,
    val psalm: String,
    val newTestament: String,
    val gospel: String?
)

@Entity
data class Scripture(
    @PrimaryKey val reference: String,
    val text: String,
    val lastUpdated: Calendar
)
