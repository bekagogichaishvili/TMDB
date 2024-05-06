package ge.gogichaishvili.tmdb.main.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ge.gogichaishvili.tmdb.main.data.local.dao.FavoriteMovieDao
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel

const val DATABASE_VERSION = 1

@Database(entities = [FavoriteMovieModel::class], version = DATABASE_VERSION, exportSchema = false)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}

/*@Database(entities = [FavoriteMovieModel::class], version = DATABASE_VERSION, exportSchema = false)
abstract class FavoriteMovieDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteMovieDatabase? = null
        fun getDatabase(context: Context): FavoriteMovieDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(
                        context,
                        FavoriteMovieDatabase::class.java,
                        "favorite_movie_database"
                    )
                        //.allowMainThreadQueries()
                        .build()
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}*/
