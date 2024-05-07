package ge.gogichaishvili.tmdb.login.data.dto

data class LoginRequestDto(
    val username: String,
    val password: String,
    val request_token: String
)
