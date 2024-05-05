package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.main.domain.local.usecase.DeleteMovieUseCase
import ge.gogichaishvili.tmdb.main.domain.local.usecase.GetAllMoviesUseCase
import ge.gogichaishvili.tmdb.main.domain.local.usecase.InsertMovieUseCase
import ge.gogichaishvili.tmdb.main.domain.network.usecase.GetPopularMoviesUseCase
import ge.gogichaishvili.tmdb.main.domain.network.usecase.MovieDetailsUseCase
import ge.gogichaishvili.tmdb.main.domain.network.usecase.SearchMoviesUseCase
import org.koin.dsl.module

object UseCaseModule {

    val module = module {

        factory { GetPopularMoviesUseCase(repository = get()) }

        factory { SearchMoviesUseCase(repository = get()) }

        factory { MovieDetailsUseCase(movieDetailsRepository = get()) }

        factory { GetAllMoviesUseCase(repository = get()) }

        factory { InsertMovieUseCase(repository = get()) }

        factory { DeleteMovieUseCase(repository = get()) }

    }
}