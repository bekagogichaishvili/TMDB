package ge.gogichaishvili.tmdb.app.di

import android.content.Context
import androidx.room.Room
import ge.gogichaishvili.tmdb.app.constants.Constants
import ge.gogichaishvili.tmdb.main.data.local.database.FavoriteMovieDatabase

fun provideDatabase (context: Context) =
    Room.databaseBuilder(context, FavoriteMovieDatabase::class.java, Constants.DATABASE_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: FavoriteMovieDatabase) = db.favoriteMovieDao()