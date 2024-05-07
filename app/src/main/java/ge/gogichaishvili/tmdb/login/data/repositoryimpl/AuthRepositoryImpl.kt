package ge.gogichaishvili.tmdb.login.data.repositoryimpl

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.app.network.getErrorExceptionMessage
import ge.gogichaishvili.tmdb.cache.UserSession
import ge.gogichaishvili.tmdb.login.data.services.AuthServiceApi
import ge.gogichaishvili.tmdb.login.domain.models.AuthUiModel
import ge.gogichaishvili.tmdb.login.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authServiceApi: AuthServiceApi
) : AuthRepository {
    override suspend fun authRequest(apiKey: String): Resource<Flow<AuthUiModel>> {
        return try {

            val response =
                authServiceApi.getAuthToken(apiKey).toDomainModel()

            UserSession.setAccessToken(response.request_token)

            val result = flow<AuthUiModel> {
                emit(response)
            }
            Resource.Success(data = result)

        } catch (e: Exception) {
            val errorMessage = (e as? HttpException)?.getErrorExceptionMessage()
            Resource.Error(message = errorMessage ?: e.message ?: "")
        }
    }

}