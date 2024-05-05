package ge.gogichaishvili.tmdb.main.domain.network.repository

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieDetailsUiModel

interface MovieDetailsRepository {
    suspend fun getMovieDetailsRequest(movieId: Int): Resource<MovieDetailsUiModel>
}