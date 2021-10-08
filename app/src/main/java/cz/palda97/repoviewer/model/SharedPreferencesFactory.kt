package cz.palda97.repoviewer.model

import android.content.Context
import cz.palda97.repoviewer.RepoViewerApplication

object SharedPreferencesFactory {

    private const val SP_NAME = "preferences.xml"

    val sharedPreferences
        get() = RepoViewerApplication.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)!!
}