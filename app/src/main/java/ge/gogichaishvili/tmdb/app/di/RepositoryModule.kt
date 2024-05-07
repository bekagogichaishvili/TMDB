package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.app.tools.SharedPreferenceManager
import ge.gogichaishvili.tmdb.login.data.repositoryimpl.AuthRepositoryImpl
import ge.gogichaishvili.tmdb.login.data.repositoryimpl.LoginRepositoryImpl
import ge.gogichaishvili.tmdb.login.domain.repository.AuthRepository
import ge.gogichaishvili.tmdb.login.domain.repository.LoginRepository
import ge.gogichaishvili.tmdb.main.data.local.data.repository_impl.FavoriteMovieRepositoryImpl
import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository
import ge.gogichaishvili.tmdb.main.data.network.repository_impl.MovieDetailsRepositoryImpl
import ge.gogichaishvili.tmdb.main.data.network.repository_impl.MovieRepositoryImpl
import ge.gogichaishvili.tmdb.main.domain.network.repository.MovieDetailsRepository
import ge.gogichaishvili.tmdb.main.domain.network.repository.MovieRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object RepositoryModule {

    val module = module {

        single { SharedPreferenceManager(context = androidApplication()) }

        single {
            MovieRepositoryImpl (movieServiceApi = get()) as MovieRepository
        }

        single {
            MovieDetailsRepositoryImpl (movieDetailsServiceApi = get()) as MovieDetailsRepository
        }

        single {
            FavoriteMovieRepositoryImpl (favoriteMovieDao = get()) as FavoriteMovieRepository
        }

        single {
            AuthRepositoryImpl (authServiceApi = get()) as AuthRepository
        }

        single {
            LoginRepositoryImpl (loginServiceApi = get()) as LoginRepository
        }

    }

}