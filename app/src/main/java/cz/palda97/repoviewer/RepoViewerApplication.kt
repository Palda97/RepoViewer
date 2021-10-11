package cz.palda97.repoviewer

import android.app.Application

/**
 * This runs on the app start.
 * Provides an access to application context
 * Initializes Koin.
 */
class RepoViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        Koin.koinInit(this)
    }

    companion object {

        private lateinit var app: Application

        /**
         * Application context
         */
        val context
            get() = app.applicationContext!!
    }
}