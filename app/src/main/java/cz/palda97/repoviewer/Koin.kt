package cz.palda97.repoviewer

import android.content.Context
import cz.palda97.repoviewer.model.db.RepoViewerDatabase
import cz.palda97.repoviewer.model.network.GithubIon
import cz.palda97.repoviewer.model.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Koin {

    private val appModule = module {
        single { RepoViewerDatabase.getInstance(androidContext()) }
        factory { get<RepoViewerDatabase>().serverDao() }
        single {
            UserRepository(
                GithubIon(),
                get()
            )
        }
    }

    fun koinInit(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
    }
}