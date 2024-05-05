package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.main.data.network.repository_impl.MovieDetailsRepositoryImpl
import ge.gogichaishvili.tmdb.main.data.network.repository_impl.MovieRepositoryImpl
import ge.gogichaishvili.tmdb.main.domain.repository.MovieDetailsRepository
import ge.gogichaishvili.tmdb.main.domain.repository.MovieRepository
import org.koin.dsl.module

object RepositoryModule {

    val module = module {

        single {
            MovieRepositoryImpl (movieServiceApi = get()) as MovieRepository
        }

        single {
            MovieDetailsRepositoryImpl (movieDetailsServiceApi = get()) as MovieDetailsRepository
        }

    }

}