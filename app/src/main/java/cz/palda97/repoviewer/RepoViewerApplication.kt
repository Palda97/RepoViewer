package cz.palda97.repoviewer

import android.app.Application

class RepoViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {

        private lateinit var app: Application

        val context
            get() = app.applicationContext!!
    }
}