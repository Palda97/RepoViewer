package cz.palda97.repoviewer.viewmodel.repodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.model.SPConstants
import cz.palda97.repoviewer.model.SharedPreferencesFactory
import cz.palda97.repoviewer.model.repository.RepoRepository
import org.koin.java.KoinJavaComponent.inject

class RepoDetailViewModel : ViewModel() {

    private val repoRepository: RepoRepository by inject(RepoRepository::class.java)
    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences

    val liveCommits
        get() = repoRepository.liveCommits
    val liveBranches
        get() = repoRepository.liveBranches

    val liveCommitStatus: LiveData<RepoRepository.StatusCode>
        get() = repoRepository.liveCommitStatus
    val liveBranchStatus: LiveData<RepoRepository.StatusCode>
        get() = repoRepository.liveBranchStatus

    val title = sharedPreferences.getString(SPConstants.REPOSITORY, "")!!

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(RepoDetailViewModel::class.java)
    }
}