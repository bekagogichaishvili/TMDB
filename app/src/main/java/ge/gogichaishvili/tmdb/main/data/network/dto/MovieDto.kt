package ge.gogichaishvili.tmdb.main.data.network.dto

import com.google.gson.annotations.SerializedName
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieUiModel

data class MovieDto(
    val id: Int,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val overview: String? = null
) {
    fun toDomainModel(): MovieUiModel {
        return MovieUiModel(
            id,
            title,
            posterPath,
            releaseDate,
            overview
        )
    }
}
