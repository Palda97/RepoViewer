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

/**
 * ViewModel for [RepoDetailActivity][cz.palda97.repoviewer.view.repodetail.RepoDetailActivity].
 */
class RepoDetailViewModel : ViewModel() {

    private val repoRepository: RepoRepository by inject(RepoRepository::class.java)
    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences

    /** [GitCommits][cz.palda97.repoviewer.model.entity.GitCommit] from db. */
    val liveCommits
        get() = repoRepository.liveCommits

    /** [Branches][cz.palda97.repoviewer.model.entity.Branch] from db. */
    val liveBranches
        get() = repoRepository.liveBranches

    /** Error status about the downloading of commits. */
    val liveCommitStatus: LiveData<RepoRepository.StatusCode>
        get() = repoRepository.liveCommitStatus

    /** Error status about the downloading of branches. */
    val liveBranchStatus: LiveData<RepoRepository.StatusCode>
        get() = repoRepository.liveBranchStatus

    /**
     * Title for [RepoDetailActivity][cz.palda97.repoviewer.view.repodetail.RepoDetailActivity].
     * It should be the [repository][cz.palda97.repoviewer.model.entity.Repository] name.
     */
    val title = sharedPreferences.getString(SPConstants.REPOSITORY, "")!!

    companion object {

        /**
         * Get an instance of this viewModel.
         */
        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(RepoDetailViewModel::class.java)

        private val context
            get() = RepoViewerApplication.context

        /**
         * Human readable message about downloading error.
         */
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