package ge.gogichaishvili.tmdb.main.domain.local.usecase

import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertMovieUseCase(private val repository: FavoriteMovieRepository) {
    suspend fun execute(movie: FavoriteMovieModel) {
        withContext(Dispatchers.IO) {
            repository.insertMovie(movie)
        }
    }
}