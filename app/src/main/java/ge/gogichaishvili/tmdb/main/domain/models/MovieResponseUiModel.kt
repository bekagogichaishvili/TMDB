package ge.gogichaishvili.tmdb.main.domain.models

import ge.gogichaishvili.tmdb.main.data.network.dto.MovieDto
import java.io.Serializable

data class MovieResponseUiModel(
    val page: Int,
    val results: List<MovieDto>
) : Serializable
