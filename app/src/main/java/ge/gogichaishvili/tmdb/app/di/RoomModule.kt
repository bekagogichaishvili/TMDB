package ge.gogichaishvili.tmdb.app.di

import org.koin.dsl.module
import androidx.room.Room
import ge.gogichaishvili.tmdb.main.data.local.database.FavoriteMovieDatabase

object RoomModule {

    val module = module {

        single {
            Room.databaseBuilder(
                get(),
                FavoriteMovieDatabase::class.java,
                "favorite_movie_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        single { get<FavoriteMovieDatabase>().favoriteMovieDao() }

    }

}