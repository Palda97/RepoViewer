package cz.palda97.repoviewer.viewmodel.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.model.SPConstants
import cz.palda97.repoviewer.model.SharedPreferencesFactory
import cz.palda97.repoviewer.model.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

class UserDetailViewModel : ViewModel() {

    private val userRepository: UserRepository by inject(UserRepository::class.java)
    private val sharedPreferences = SharedPreferencesFactory.sharedPreferences

    val liveRepositories
        get() = userRepository.liveRepositories

    val title = sharedPreferences.getString(SPConstants.USER, "")!!

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(UserDetailViewModel::class.java)
    }
}