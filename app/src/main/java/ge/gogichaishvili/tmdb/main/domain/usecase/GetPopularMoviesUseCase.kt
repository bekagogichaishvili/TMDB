package ge.gogichaishvili.tmdb.main.domain.usecase

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.models.MovieResponseUiModel
import ge.gogichaishvili.tmdb.main.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    suspend fun execute(page: Int): Resource<MovieResponseUiModel> {
        return repository.getPopularMoviesRequest(page)
    }
}
