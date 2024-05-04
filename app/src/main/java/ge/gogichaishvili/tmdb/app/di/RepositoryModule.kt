package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.main.domain.repository.MovieRepository
import org.koin.dsl.module

object RepositoryModule {

    val module = module {

        single {
            MovieRepository (apiServices = get()) as MovieRepository
        }

    }

}