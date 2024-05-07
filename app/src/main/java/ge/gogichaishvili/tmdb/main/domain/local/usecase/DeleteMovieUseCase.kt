package ge.gogichaishvili.tmdb.main.domain.local.usecase

import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteMovieUseCase(private val repository: FavoriteMovieRepository) {
    suspend fun execute(id: Long): Boolean = withContext(Dispatchers.IO) {
        repository.deleteMovie(id)
    }
}
