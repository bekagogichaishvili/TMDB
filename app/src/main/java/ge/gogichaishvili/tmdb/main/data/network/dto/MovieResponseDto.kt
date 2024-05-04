package ge.gogichaishvili.tmdb.main.data.network.dto

data class MovieResponseDto(
    val page: Int,
    val results: List<Movie>
)
