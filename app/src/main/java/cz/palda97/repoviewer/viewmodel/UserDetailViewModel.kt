package cz.palda97.repoviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class UserDetailViewModel : ViewModel() {

    companion object {

        fun getInstance(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(UserDetailViewModel::class.java)
    }
}