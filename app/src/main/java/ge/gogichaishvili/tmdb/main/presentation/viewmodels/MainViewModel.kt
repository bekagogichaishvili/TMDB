package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ge.gogichaishvili.tmdb.main.data.network.paging.MoviesPagingSource
import ge.gogichaishvili.tmdb.main.domain.models.MovieUiModel
import ge.gogichaishvili.tmdb.main.domain.usecase.GetPopularMoviesUseCase
import ge.gogichaishvili.tmdb.main.domain.usecase.SearchMoviesUseCase
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : BaseViewModel() {

    fun getMovies(
        query: String?
    ): Flow<PagingData<MovieUiModel>> {
        return Pager(PagingConfig(pageSize = 1)) {
            MoviesPagingSource(getPopularMoviesUseCase, searchMoviesUseCase, query)
        }.flow.cachedIn(viewModelScope)
    }

}