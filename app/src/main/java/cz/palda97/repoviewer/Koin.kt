package cz.palda97.repoviewer

import android.content.Context
import cz.palda97.repoviewer.model.db.RepoViewerDatabase
import cz.palda97.repoviewer.model.network.GithubIon
import cz.palda97.repoviewer.model.repository.RepoRepository
import cz.palda97.repoviewer.model.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Injection framework
 */
object Koin {

    private val appModule = module {
        single { RepoViewerDatabase.getInstance(androidContext()) }
        factory { get<RepoViewerDatabase>().repositoryDao() }
        factory { get<RepoViewerDatabase>().commitDao() }
        factory { get<RepoViewerDatabase>().branchDao() }
        single {
            UserRepository(
                GithubIon(),
                get()
            )
        }
        single {
            RepoRepository(
                GithubIon(),
                get(),
                get()
            )
        }
    }

    /**
     * Initialize Koin.
     * Run this at the application start.
     */
    fun koinInit(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
    }
}