package ge.gogichaishvili.tmdb.main.domain.network.repository

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieResponseUiModel

interface MovieRepository {
    suspend fun getPopularMoviesRequest(page: Int): Resource<MovieResponseUiModel>
    suspend fun searchMoviesRequest(query: String, page: Int): Resource<MovieResponseUiModel>
}
