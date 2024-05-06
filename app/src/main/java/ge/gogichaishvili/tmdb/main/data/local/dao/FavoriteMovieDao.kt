package ge.gogichaishvili.tmdb.main.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ge.gogichaishvili.tmdb.app.constants.Constants.TABLE_NAME
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(item: FavoriteMovieModel): Long

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<FavoriteMovieModel>



    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC")
    suspend fun getMovieList(): List<FavoriteMovieModel>?

    //@Query("SELECT * FROM $TABLE_NAME ORDER BY id ASC")
    //suspend fun getAllMovies(): LiveData<List<FavoriteMovieModel>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $TABLE_NAME.id = :id")
    suspend fun getMovie(id: String): FavoriteMovieModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun createAll(result: List<FavoriteMovieModel>)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(model: FavoriteMovieModel)

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteMovie(id: String)

    @Update
    suspend fun update(model: FavoriteMovieModel)

    @Query("SELECT * FROM $TABLE_NAME LIMIT :pageSize OFFSET :pageIndex")
    suspend fun getMoviePage(pageSize: Int, pageIndex: Int): List<FavoriteMovieModel>?

}
