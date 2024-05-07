package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 100,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { RoomMoviesPagingSource(getAllMoviesUseCase) }
        ).flow.cachedIn(viewModelScope)
    }


    private val _statusMessage = MutableLiveData<Boolean>()
    val statusMessage: LiveData<Boolean> = _statusMessage

    fun deleteMovie(id: Long) {
        viewModelScope.launch {
            val success = deleteMovieUseCase.execute(id)
            _statusMessage.value = success
        }
    }

}