package cz.palda97.repoviewer.viewmodel.aboutapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.RepoViewerApplication

/**
 * ViewModel for [AboutAppActivity][cz.palda97.repoviewer.view.aboutapp.AboutAppActivity].
 */
class AboutAppViewModel : ViewModel() {

    private val context
        get() = RepoViewerApplication.context

    private val text =
        "${context.getString(R.string.made_with_love)}$BR${context.getString(R.string.author)}"

    /**
     * The main text for [AboutAppActivity][cz.palda97.repoviewer.view.aboutapp.AboutAppActivity].
     */
    val aboutText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

    /**
     * Opens Github page of this application.
     */
    fun showSourceButton(context: Context) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Palda97/RepoViewer")
            )
        )
    }

    companion object {

        /**
         * Get an instance of this viewModel.
         */
        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(AboutAppViewModel::class.java)

        private const val BR = "<br>"
    }
}