package ge.gogichaishvili.tmdb.login.domain.repository

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.login.domain.models.AuthUiModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authRequest(apiKey: String): Resource<Flow<AuthUiModel>>
}