package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.data.local.paging.RoomMoviesPagingSource
import ge.gogichaishvili.tmdb.main.domain.local.usecase.DeleteMovieUseCase
import ge.gogichaishvili.tmdb.main.domain.local.usecase.GetAllMoviesUseCase
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : BaseViewModel() {

    fun getMovies(): Flow<PagingData<FavoriteMovieModel>> {
        return Pager(PagingConfig(pageSize = 1)) {
            RoomMoviesPagingSource(getAllMoviesUseCase)
        }.flow.cachedIn(viewModelScope)
    }


   /* fun getMovies_(): Flow<PagingData<FavoriteMovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // More standard page size
                enablePlaceholders = true, // Enable placeholders if your UI supports it and you need smoother scrolling
                maxSize = 100, // Consider setting a maxSize that is some multiple of pageSize
                initialLoadSize = 40 // Consider setting an initialLoadSize that is typically twice the pageSize
            ),
            pagingSourceFactory = { RoomMoviesPagingSource(getAllMoviesUseCase) }
        ).flow.cachedIn(viewModelScope)
    }*/


    fun deleteMovie(movie: FavoriteMovieModel) {
        viewModelScope.launch {
            deleteMovieUseCase.execute(movie)
        }
    }
}