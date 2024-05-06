package ge.gogichaishvili.tmdb.main.domain.local.usecase

import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllMoviesUseCase(private val repository: FavoriteMovieRepository) {
    suspend fun execute(limit: Int, offset: Int): List<FavoriteMovieModel> = withContext(Dispatchers.IO) {
        repository.getAllMovies(limit, offset)
    }
}