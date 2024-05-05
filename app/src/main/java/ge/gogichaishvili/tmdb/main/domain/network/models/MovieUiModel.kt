package ge.gogichaishvili.tmdb.main.domain.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieUiModel(
    val id: Int,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val overview: String? = null
) : Serializable
