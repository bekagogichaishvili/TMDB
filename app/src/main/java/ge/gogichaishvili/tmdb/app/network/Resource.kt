package ge.gogichaishvili.tmdb.app.network

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(
        message: String,
        data: T? = null,
        errorCode: String? = null,
        val httpCode: Int? = null,
        ex: Throwable? = null
    ) :
        Resource<T>(data, message, errorCode)

    val isSuccess: Boolean
        get() = this is Success && data != null
}
