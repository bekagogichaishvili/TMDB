package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ge.gogichaishvili.tmdb.main.domain.local.usecase.DeleteMovieUseCase
import ge.gogichaishvili.tmdb.main.domain.local.usecase.GetAllMoviesUseCase
import ge.gogichaishvili.tmdb.main.domain.local.usecase.InsertMovieUseCase
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : BaseViewModel() {

    val allMovies: LiveData<List<FavoriteMovieModel>> = getAllMoviesUseCase.execute()

    fun insertMovie(movie: FavoriteMovieModel) {
        viewModelScope.launch {
            insertMovieUseCase.execute(movie)
        }
    }

    fun deleteMovie(movie: FavoriteMovieModel) {
        viewModelScope.launch {
            deleteMovieUseCase.execute(movie)
        }
    }
}