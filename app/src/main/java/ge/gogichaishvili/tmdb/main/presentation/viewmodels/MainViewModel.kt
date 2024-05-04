package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ge.gogichaishvili.tmdb.app.constants.Constants
import ge.gogichaishvili.tmdb.main.data.network.dto.Movie
import ge.gogichaishvili.tmdb.main.data.network.paging.MoviesPagingSource
import ge.gogichaishvili.tmdb.main.domain.repository.MovieRepository
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: MovieRepository) : BaseViewModel() {

    init {
        getMovies(null)
    }

    fun getMovies(
        query: String?
    ): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 5)) {
            MoviesPagingSource(repository, Constants.API_KEY, query)
        }.flow.cachedIn(viewModelScope)
    }

}