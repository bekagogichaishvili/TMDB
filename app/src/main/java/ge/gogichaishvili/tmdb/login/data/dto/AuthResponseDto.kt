package ge.gogichaishvili.tmdb.login.data.dto

import ge.gogichaishvili.tmdb.login.domain.models.AuthUiModel

data class AuthResponseDto(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
) {
    fun toDomainModel(): AuthUiModel {
        return AuthUiModel(
            success,
            expires_at,
            request_token
        )
    }
}
