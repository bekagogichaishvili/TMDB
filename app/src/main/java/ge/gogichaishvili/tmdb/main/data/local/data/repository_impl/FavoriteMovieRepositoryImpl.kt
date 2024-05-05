package ge.gogichaishvili.tmdb.main.data.local.data.repository_impl

import androidx.lifecycle.LiveData
import ge.gogichaishvili.tmdb.main.data.local.dao.FavoriteMovieDao
import ge.gogichaishvili.tmdb.main.domain.local.repository.FavoriteMovieRepository
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

class FavoriteMovieRepositoryImpl(private val favoriteMovieDao: FavoriteMovieDao) :
    FavoriteMovieRepository {

    override suspend fun insertMovie(model: FavoriteMovieModel) {
        favoriteMovieDao.insert(model)
    }

    override suspend fun deleteMovie(model: FavoriteMovieModel) {
        favoriteMovieDao.delete(model)
    }

    override fun getAllMovies(): LiveData<List<FavoriteMovieModel>> {
        return favoriteMovieDao.getAllMovies()
    }
}