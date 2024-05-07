package ge.gogichaishvili.tmdb.main.domain.local.repository

import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

interface FavoriteMovieRepository {
    suspend fun insertMovie(movie: FavoriteMovieModel):Boolean
    suspend fun deleteMovie(id: Long):Boolean
    suspend fun getAllMovies(limit: Int, offset: Int): List<FavoriteMovieModel>
}