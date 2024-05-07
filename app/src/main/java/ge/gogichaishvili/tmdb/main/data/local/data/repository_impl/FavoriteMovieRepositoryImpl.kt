package ge.gogichaishvili.tmdb.main.data.local.data.repository_impl

import ge.gogichaishvili.tmdb.main.data.local.dao.FavoriteMovieDao
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository

class FavoriteMovieRepositoryImpl(private val favoriteMovieDao: FavoriteMovieDao) :
    FavoriteMovieRepository {

    override suspend fun insertMovie(movie: FavoriteMovieModel): Boolean {
        return try {
            val result = favoriteMovieDao.insertMovie(movie)
            result > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteMovie(id: Long): Boolean {
        return try {
            val result = favoriteMovieDao.deleteMovieById(id)
            result > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getAllMovies(limit: Int, offset: Int): List<FavoriteMovieModel> {
        return favoriteMovieDao.getPagedList(limit, offset)
    }

}