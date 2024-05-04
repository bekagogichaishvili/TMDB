package ge.gogichaishvili.tmdb.app

import android.app.Application
import ge.gogichaishvili.tmdb.app.di.NetworkModule
import ge.gogichaishvili.tmdb.app.di.RepositoryModule
import ge.gogichaishvili.tmdb.app.di.SessionModule
import ge.gogichaishvili.tmdb.app.di.UseCaseModule
import ge.gogichaishvili.tmdb.app.di.ViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    ViewModelModules.modules,
                    RepositoryModule.module,
                    UseCaseModule.module,
                    NetworkModule.modules,
                    SessionModule.module
                )
            )
        }
    }

}