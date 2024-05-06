package ge.gogichaishvili.tmdb.main.domain.local.repository

import androidx.lifecycle.LiveData
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

interface FavoriteMovieRepository {
    suspend fun insertMovie(movie: FavoriteMovieModel):Boolean
    suspend fun deleteMovie(model: FavoriteMovieModel)
    fun getAllMovies(): LiveData<List<FavoriteMovieModel>>
}