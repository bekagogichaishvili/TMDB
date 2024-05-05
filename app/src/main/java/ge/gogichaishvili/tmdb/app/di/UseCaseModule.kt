package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.main.domain.usecase.GetPopularMoviesUseCase
import ge.gogichaishvili.tmdb.main.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

object UseCaseModule {

    val module = module {

        factory { GetPopularMoviesUseCase(repository = get()) }

        factory { SearchMoviesUseCase(repository = get()) }

    }
}