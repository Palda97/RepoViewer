package cz.palda97.repoviewer.viewmodel.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.model.SPConstants
import cz.palda97.repoviewer.model.SharedPreferencesFactory
import cz.palda97.repoviewer.model.SingleLiveEvent
import cz.palda97.repoviewer.model.entity.Repository
import cz.palda97.repoviewer.model.repository.RepoRepository
import cz.palda97.repoviewer.model.repository.UserRepository
import cz.palda97.repoviewer.view.repodetail.RepoDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/**
 * ViewModel for [UserDetailActivity][cz.palda97.repoviewer.view.userdetail.UserDetailActivity].
 */
class UserDetailViewModel : ViewModel() {

    private val userRepository: UserRepository by inject(UserRepository::class.java)
    private val repoRepository: RepoRepository by inject(RepoRepository::class.java)
    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences

    /** [Repositories][Repository] from db */
    val liveRepositories
        get() = userRepository.liveRepositories

    /**
     * Title for [UserDetailActivity][cz.palda97.repoviewer.view.userdetail.UserDetailActivity].
     * It should be the user's name.
     */
    val title = sharedPreferences.getString(SPConstants.USER, "")!!

    /**
     * Starts the downloading of [commits][cz.palda97.repoviewer.model.entity.GitCommit]
     * and [branches][cz.palda97.repoviewer.model.entity.Branch] and opens the [RepoDetailActivity].
     */
    fun repositoryOnClick(repository: Repository) {
        sharedPreferences.edit().putString(SPConstants.REPOSITORY, repository.name).apply()
        CoroutineScope(Dispatchers.IO).launch {
            repoRepository.cache(repository.fullName)
        }
        _liveStartActivity.value = RepoDetailActivity::class.java
    }

    private val _liveStartActivity = SingleLiveEvent<Class<*>>()

    /**
     * Telling the observer what activity should be launched.
     */
    val liveStartActivity: LiveData<Class<*>>
        get() = _liveStartActivity

    companion object {

        /**
         * Get an instance of this viewModel.
         */
        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(UserDetailViewModel::class.java)
    }
}