package cz.palda97.repoviewer.viewmodel.aboutapp

import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.RepoViewerApplication

class AboutAppViewModel : ViewModel() {

    private val context
        get() = RepoViewerApplication.context

    private val text =
        "${context.getString(R.string.made_with_love)}$BR${context.getString(R.string.author)}"

    val aboutText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(AboutAppViewModel::class.java)

        private const val BR = "<br>"
    }
}