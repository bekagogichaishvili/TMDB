package ge.gogichaishvili.tmdb.cache

object UserSession {

    private var accessToken: String? = null

    fun getAccessToken() = accessToken

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    fun clearSession() {
        accessToken = null
    }
}