package cz.palda97.repoviewer

import android.app.Application
import cz.palda97.repoviewer.model.db.RepoViewerDatabase
import cz.palda97.repoviewer.model.network.GithubIon
import cz.palda97.repoviewer.model.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class RepoViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        koinInit()
    }

    private fun koinInit() {
        val appModule = module {
            single { RepoViewerDatabase.getInstance(androidContext()) }
            factory { get<RepoViewerDatabase>().serverDao() }
            single {
                UserRepository(
                    GithubIon(),
                    get()
                )
            }
        }
        startKoin {
            androidLogger()
            androidContext(this@RepoViewerApplication)
            modules(appModule)
        }
    }

    companion object {

        private lateinit var app: Application

        val context
            get() = app.applicationContext!!
    }
}