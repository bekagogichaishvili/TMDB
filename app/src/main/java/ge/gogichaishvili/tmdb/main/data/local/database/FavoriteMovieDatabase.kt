package ge.gogichaishvili.tmdb.main.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.gogichaishvili.tmdb.main.data.local.dao.FavoriteMovieDao
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

const val DATABASE_VERSION = 1

@Database(entities = [FavoriteMovieModel::class], version = DATABASE_VERSION, exportSchema = false)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
