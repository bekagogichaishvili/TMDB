package ge.gogichaishvili.tmdb.login.data.services

import ge.gogichaishvili.tmdb.login.data.dto.AuthResponseDto
import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AuthServiceApi {
    @Headers("platform: Web")
    @GET(ApiEndpoints.NEW)
    suspend fun getAuthToken(
        @Query("api_key")
        apiKey: String
    ): AuthResponseDto
}