package ge.gogichaishvili.tmdb.main.domain.repository

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.models.MovieDetailsUiModel

interface MovieDetailsRepository {
    suspend fun getMovieDetailsRequest(movieId: Int): Resource<MovieDetailsUiModel>
}