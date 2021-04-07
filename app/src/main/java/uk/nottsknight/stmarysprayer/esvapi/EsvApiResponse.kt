package uk.nottsknight.stmarysprayer.esvapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EsvApiResponse(
    val query: String,
    val canonical: String,
    val parsed: Array<Array<String>>,
    @SerialName("passage_meta") val passageMeta: Array<EsvPassageMeta>,
    val passages: Array<String>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EsvApiResponse

        if (query != other.query) return false
        if (canonical != other.canonical) return false

        return true
    }

    override fun hashCode(): Int {
        var result = query.hashCode()
        result = 31 * result + canonical.hashCode()
        return result
    }
}

@Serializable
data class EsvPassageMeta(
    val canonical: String,
    @SerialName("chapter_start") val chapterStart: Array<Int>,
    @SerialName("chapter_end") val chapterEnd: Array<Int>,
    @SerialName("prev_verse") val prevVerse: Int?,
    @SerialName("next_verse") val nextVerse: Int?,
    @SerialName("prev_chapter") val prevChapter: Array<Int>?,
    @SerialName("next_chapter") val nextChapter: Array<Int>?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EsvPassageMeta

        if (canonical != other.canonical) return false

        return true
    }

    override fun hashCode(): Int {
        return canonical.hashCode()
    }
}