package ge.gogichaishvili.tmdb.app.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class RequestInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = ""
        val requestBuilder = chain.request().newBuilder()

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        requestBuilder.addHeader("Accept-Language", Locale.getDefault().language)

        val request = requestBuilder.build()

        val response = chain.proceed(request)

        return response
    }

}