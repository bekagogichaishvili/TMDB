package ge.gogichaishvili.tmdb.main.domain.usecase

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.models.MovieResponseUiModel
import ge.gogichaishvili.tmdb.main.domain.repository.MovieRepository

class SearchMoviesUseCase(private val repository: MovieRepository) {
    suspend fun execute(query: String, page: Int): Resource<MovieResponseUiModel> {
        return repository.searchMoviesRequest(query, page)
    }
}
