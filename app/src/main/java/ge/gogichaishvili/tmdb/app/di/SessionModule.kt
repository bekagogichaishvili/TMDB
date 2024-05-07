package ge.gogichaishvili.tmdb.app.di

import ge.gogichaishvili.tmdb.cache.UserSession
import org.koin.dsl.module

object SessionModule {
    val module = module {
        single { UserSession }
    }
}