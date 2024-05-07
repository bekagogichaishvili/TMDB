package ge.gogichaishvili.tmdb.login.domain.models

import java.io.Serializable

class AuthUiModel (
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)  : Serializable