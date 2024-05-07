package ge.gogichaishvili.tmdb.login.data.services

import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.login.data.dto.AuthResponseDto
import ge.gogichaishvili.tmdb.login.data.dto.LoginRequestDto
import retrofit2.http.*

interface LoginServiceApi {
    @POST(ApiEndpoints.LOGIN)
    suspend fun login(
        @Query("api_key")
        apiKey: String,
        @Body loginData: LoginRequestDto
    ): AuthResponseDto
}