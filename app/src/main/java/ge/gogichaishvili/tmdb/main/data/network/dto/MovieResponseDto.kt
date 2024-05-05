package ge.gogichaishvili.tmdb.main.data.network.dto

import ge.gogichaishvili.tmdb.main.domain.models.MovieResponseUiModel

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>
) {
    fun toDomainModel(): MovieResponseUiModel {
        return MovieResponseUiModel(
            page,
            results
        )
    }
}
