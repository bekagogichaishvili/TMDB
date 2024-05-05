package ge.gogichaishvili.tmdb.main.data.network.dto

import com.google.gson.annotations.SerializedName
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieDetailsUiModel

data class MovieDetailsResponseDto(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("title")
    val title: String? = null,

    ) {
    fun toDomainModel(): MovieDetailsUiModel {
        return MovieDetailsUiModel(
            id = id,
            backdropPath = backdropPath,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity
        )
    }
}

