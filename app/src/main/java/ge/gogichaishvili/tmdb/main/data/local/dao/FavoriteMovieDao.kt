package ge.gogichaishvili.tmdb.main.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ge.gogichaishvili.tmdb.app.constants.Constants.TABLE_NAME
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(item: FavoriteMovieModel): Long


    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC")
    fun getAll(): List<FavoriteMovieModel>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<FavoriteMovieModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem_(model: FavoriteMovieModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun createAll(result: List<FavoriteMovieModel>)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

    @Delete
    fun delete(model: FavoriteMovieModel)

    @Update
    fun update(model: FavoriteMovieModel)

}
