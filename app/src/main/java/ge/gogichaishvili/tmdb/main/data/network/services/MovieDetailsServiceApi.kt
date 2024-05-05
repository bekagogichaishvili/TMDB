package ge.gogichaishvili.tmdb.main.data.network.services

import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.main.data.network.dto.MovieDetailsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsServiceApi {
    @GET(ApiEndpoints.MOVIE_DETAILS + "/{id}")
    suspend fun getMovieDetails(
        @Path("id")
        movieId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<MovieDetailsResponseDto>
}