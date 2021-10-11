package cz.palda97.repoviewer.viewmodel.repodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.model.SPConstants
import cz.palda97.repoviewer.model.SharedPreferencesFactory

class RepoDetailViewModel : ViewModel() {

    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences

    val title = sharedPreferences.getString(SPConstants.REPOSITORY, "")!!

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(RepoDetailViewModel::class.java)
    }
}