package ge.gogichaishvili.tmdb.main.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movie_list ORDER BY id DESC")
    fun getAll(): List<FavoriteMovieModel>

    @Query("SELECT * FROM favorite_movie_list ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<FavoriteMovieModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: FavoriteMovieModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun createAll(result: List<FavoriteMovieModel>)

    @Query("DELETE FROM favorite_movie_list")
    fun deleteAll()

    @Delete
    fun delete(model: FavoriteMovieModel)

    @Update
    fun update(model: FavoriteMovieModel)

}
