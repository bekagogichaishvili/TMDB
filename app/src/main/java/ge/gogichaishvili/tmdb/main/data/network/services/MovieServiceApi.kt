package ge.gogichaishvili.tmdb.main.data.network.services

import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.main.data.network.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceApi {
    @GET(ApiEndpoints.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieResponseDto>

    @GET(ApiEndpoints.SEARCH_MOVIES)
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String?,
        @Query("page") page: Int
    ): Response<MovieResponseDto>
}