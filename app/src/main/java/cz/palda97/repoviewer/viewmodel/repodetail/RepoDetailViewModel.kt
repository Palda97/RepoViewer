package cz.palda97.repoviewer.viewmodel.repodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.RepoViewerApplication
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

        private val context
            get() = RepoViewerApplication.context
        val RepoRepository.StatusCode.msg
            get() = when (this) {
                RepoRepository.StatusCode.NOT_FOUND -> context.getString(R.string.user_has_not_been_found)
                RepoRepository.StatusCode.CONNECTION_PROBLEM -> context.getString(R.string.there_is_a_connection_problem)
                RepoRepository.StatusCode.PARSING_ERROR -> context.getString(R.string.there_is_a_parsing_problem)
                RepoRepository.StatusCode.LOADING -> context.getString(R.string.internal_error)
                RepoRepository.StatusCode.OK -> context.getString(R.string.internal_error)
            }
    }
}