package cz.palda97.repoviewer.view.mainscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ActivityMainBinding
import cz.palda97.repoviewer.model.repository.UserRepository
import cz.palda97.repoviewer.viewmodel.mainscreen.MainScreenViewModel

/**
 * The main activity. User can either fill in a Github username or click on about app button.
 */
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by lazy {
        MainScreenViewModel.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupShowRepositoriesButton()
        setupAboutAppButton()
        setupErrorCode()
        checkForNewActivityRequest()
    }

    private fun setupShowRepositoriesButton() {
        binding.showRepositoriesButton.setOnClickListener {
            viewModel.showRepositoriesButton(binding.nameField.text.toString())
        }
    }

    private fun setupAboutAppButton() {
        binding.aboutAppButton.setOnClickListener {
            viewModel.aboutAppButton()
        }
    }

    private val UserRepository.ErrorCode.msg
        get() = when (this) {
            UserRepository.ErrorCode.NOT_FOUND -> getString(R.string.user_has_not_been_found)
            UserRepository.ErrorCode.CONNECTION_PROBLEM -> getString(R.string.there_is_a_connection_problem)
            UserRepository.ErrorCode.PARSING_ERROR -> getString(R.string.there_is_a_parsing_problem)
        }

    private fun setupErrorCode() {
        viewModel.liveErrorStatus.observe(this, {
            val errorCode = it ?: return@observe
            Snackbar
                .make(binding.root, errorCode.msg, Snackbar.LENGTH_LONG)
                .show()
        })
    }

    private fun checkForNewActivityRequest() {
        viewModel.liveStartActivity.observe(this, {
            val activityClass = it ?: return@observe
            val intent = Intent(this, activityClass)
            startActivity(intent)
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}