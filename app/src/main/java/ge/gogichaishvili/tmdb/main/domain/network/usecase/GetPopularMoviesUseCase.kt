package ge.gogichaishvili.tmdb.main.domain.network.usecase

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieResponseUiModel
import ge.gogichaishvili.tmdb.main.domain.network.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPopularMoviesUseCase (private val repository: MovieRepository) {
    suspend fun execute(page: Int): Resource<MovieResponseUiModel> {
        return withContext(Dispatchers.IO) {
            repository.getPopularMoviesRequest(page)
        }
    }
}
