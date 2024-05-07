package ge.gogichaishvili.tmdb.login.data.repositoryimpl

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.app.network.getErrorExceptionMessage
import ge.gogichaishvili.tmdb.cache.UserSession
import ge.gogichaishvili.tmdb.login.data.dto.LoginRequestDto
import ge.gogichaishvili.tmdb.login.data.services.LoginServiceApi
import ge.gogichaishvili.tmdb.login.domain.models.AuthUiModel
import ge.gogichaishvili.tmdb.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception

class LoginRepositoryImpl(
    private val loginServiceApi: LoginServiceApi
) : LoginRepository {

    override suspend fun loginRequest(
        apiKey: String,
        loginData: LoginRequestDto
    ): Resource<Flow<AuthUiModel>> {
        return try {

            val response = loginServiceApi.login(apiKey, loginData).toDomainModel()

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

