package cz.palda97.repoviewer.viewmodel

import androidx.lifecycle.*
import cz.palda97.repoviewer.model.SharedPreferencesFactory
import cz.palda97.repoviewer.model.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainScreenViewModel : ViewModel() {

    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences
    private val repository: UserRepository by inject(UserRepository::class.java)

    fun showRepositoriesButton(username: String) {
        sharedPreferences.edit().putString(USER, username).apply()
        CoroutineScope(Dispatchers.IO).launch {
            repository.cacheRepositories(username)
        }
    }

    fun aboutAppButton() {
        TODO()
    }

    companion object {

        private const val USER = "USER"

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(MainScreenViewModel::class.java)
    }
}