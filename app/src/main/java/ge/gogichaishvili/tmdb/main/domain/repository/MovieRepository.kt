package ge.gogichaishvili.tmdb.main.domain.repository

import ge.gogichaishvili.tmdb.main.data.network.services.MovieServiceApi

class MovieRepository (private val apiServices: MovieServiceApi) {
    suspend fun getPopularMoviesRequest(
        apiKey: String,
        page: Int
    ) = apiServices.getPopularMovies (apiKey, page)

    suspend fun searchMoviesRequest(
        apiKey: String,
        query: String?,
        page: Int
    ) = apiServices.searchMovies (apiKey, query, page)
}