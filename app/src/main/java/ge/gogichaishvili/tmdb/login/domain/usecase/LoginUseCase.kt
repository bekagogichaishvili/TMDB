package ge.gogichaishvili.tmdb.login.domain.usecase

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.login.data.dto.LoginRequestDto
import ge.gogichaishvili.tmdb.login.domain.models.AuthUiModel
import ge.gogichaishvili.tmdb.login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun login(
        apiKey: String,
        username: String,
        password: String,
        request_token: String
    ): Resource<Flow<AuthUiModel>> =
        withContext(Dispatchers.IO) {

            val loginData =
                LoginRequestDto(
                    username = username,
                    password = password,
                    request_token = request_token
                )

            val result = loginRepository.loginRequest(apiKey, loginData)

            if (result.isSuccess) {
                Resource.Success(data = result.data!!)
            } else {
                Resource.Error(
                    message = result.message ?: "",
                    errorCode = (result as Resource.Error).errorCode
                )
            }
        }
}
