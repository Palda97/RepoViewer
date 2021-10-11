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

class MainScreenViewModel : ViewModel() {

    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences
    private val repository: UserRepository by inject(UserRepository::class.java)

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

    fun aboutAppButton() {
        _liveStartActivity.value = AboutAppActivity::class.java
    }

    val liveErrorStatus: LiveData<UserRepository.ErrorCode>
        get() = repository.liveErrorCode

    val liveLoading: LiveData<Boolean>
        get() = repository.liveRepositoryLoading

    private val _liveStartActivity = SingleLiveEvent<Class<*>>()
    val liveStartActivity: LiveData<Class<*>>
        get() = _liveStartActivity

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(MainScreenViewModel::class.java)
    }
}