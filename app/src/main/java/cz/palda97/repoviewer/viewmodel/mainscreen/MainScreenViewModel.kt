package cz.palda97.repoviewer.viewmodel.mainscreen

import androidx.lifecycle.*
import cz.palda97.repoviewer.model.SPConstants
import cz.palda97.repoviewer.model.SharedPreferencesFactory
import cz.palda97.repoviewer.model.SingleLiveEvent
import cz.palda97.repoviewer.model.repository.UserRepository
import cz.palda97.repoviewer.view.aboutapp.AboutAppActivity
import cz.palda97.repoviewer.view.userdetail.UserDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/**
 * ViewModel for [MainActivity][cz.palda97.repoviewer.view.mainscreen.MainActivity].
 */
class MainScreenViewModel : ViewModel() {

    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences
    private val repository: UserRepository by inject(UserRepository::class.java)

    /**
     * Tries to download list of user's repositories and starts [UserDetailActivity] on success.
     */
    fun showRepositoriesButton(username: String) {
        repository.liveRepositoryLoading.value = true
        sharedPreferences.edit().putString(SPConstants.USER, username).apply()
        CoroutineScope(Dispatchers.IO).launch {
            if (repository.cacheRepositories(username)) {
                _liveStartActivity.postValue(UserDetailActivity::class.java)
            }
            repository.liveRepositoryLoading.postValue(false)
        }
    }

    /**
     * Opens [AboutAppActivity].
     */
    fun aboutAppButton() {
        _liveStartActivity.value = AboutAppActivity::class.java
    }

    /**
     * Error status about the downloading of repositories.
     */
    val liveErrorStatus: LiveData<UserRepository.ErrorCode>
        get() = repository.liveErrorCode

    /**
     * Is the downloading in progress.
     */
    val liveLoading: LiveData<Boolean>
        get() = repository.liveRepositoryLoading

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
            ViewModelProvider(owner).get(MainScreenViewModel::class.java)
    }
}