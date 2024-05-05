package ge.gogichaishvili.tmdb.main.domain.local.usecase

import androidx.lifecycle.LiveData
import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

class GetAllMoviesUseCase(private val repository: FavoriteMovieRepository) {
    fun execute(): LiveData<List<FavoriteMovieModel>> {
        return repository.getAllMovies()
    }
}