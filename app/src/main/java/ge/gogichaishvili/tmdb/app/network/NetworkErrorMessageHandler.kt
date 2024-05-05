package ge.gogichaishvili.tmdb.app.network

import retrofit2.HttpException

fun HttpException.getErrorExceptionMessage(): String {
    return try {
        val errorResponse = response()
        if (errorResponse != null) {
            errorResponse.errorBody()?.string() ?: ""
        } else {
            "Something went wrong"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Something went wrong"
    }
}



