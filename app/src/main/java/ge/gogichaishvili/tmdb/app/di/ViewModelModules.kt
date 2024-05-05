package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.main.presentation.viewmodels.DetailsViewModel
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.FavoritesViewModel
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModules {
    val modules = module {

        viewModel {
            MainViewModel(getPopularMoviesUseCase = get(), searchMoviesUseCase = get())
        }

        viewModel {
            DetailsViewModel()
        }

        viewModel {
            FavoritesViewModel()
        }

    }
}

