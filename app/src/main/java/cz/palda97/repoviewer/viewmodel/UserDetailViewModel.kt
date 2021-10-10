package cz.palda97.repoviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.palda97.repoviewer.model.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

class UserDetailViewModel : ViewModel() {

    private val userRepository: UserRepository by inject(UserRepository::class.java)

    val liveRepositories
        get() = userRepository.liveRepositories

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(UserDetailViewModel::class.java)
    }
}