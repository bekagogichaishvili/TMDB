package ge.gogichaishvili.tmdb.main.domain.models

import java.io.Serializable

data class MovieDetailsUiModel(
    val id: Int? = null,
    val backdropPath: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null
) : Serializable
