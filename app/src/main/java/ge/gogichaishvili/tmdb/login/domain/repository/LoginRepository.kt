package ge.gogichaishvili.tmdb.login.domain.repository

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.login.data.dto.LoginRequestDto
import ge.gogichaishvili.tmdb.login.domain.models.AuthUiModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun loginRequest(apiKey: String, loginData: LoginRequestDto): Resource<Flow<AuthUiModel>>
}