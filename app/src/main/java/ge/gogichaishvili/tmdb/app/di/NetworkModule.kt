package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.app.network.RequestInterceptor
import ge.gogichaishvili.tmdb.login.data.services.AuthServiceApi
import ge.gogichaishvili.tmdb.login.data.services.LoginServiceApi
import ge.gogichaishvili.tmdb.main.data.network.services.MovieDetailsServiceApi
import ge.gogichaishvili.tmdb.main.data.network.services.MovieServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    val modules = module {

        single {
            RequestInterceptor()
        }

        single { provideRetrofit(client = get(), baseUrl = ApiEndpoints.BASE_URL) }

        single { provideOkHttpClient(requestInterceptor = get()) }

        single {
            get<Retrofit>().create(MovieServiceApi::class.java)
        }

        single {
            get<Retrofit>().create(MovieDetailsServiceApi::class.java)
        }

        single {
            get<Retrofit>().create(AuthServiceApi::class.java)
        }

        single {
            get<Retrofit>().create(LoginServiceApi::class.java)
        }

    }

    private fun provideOkHttpClient(
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(requestInterceptor)

        return clientBuilder.build()
    }

    private fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
