package ge.gogichaishvili.tmdb.app.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object RoomModule {

    val module = module {

        single { provideDatabase(androidContext()) }
        single { provideDao(get()) }

    }

}