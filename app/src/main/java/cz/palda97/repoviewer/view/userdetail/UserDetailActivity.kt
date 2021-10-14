package cz.palda97.repoviewer.view.userdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ActivityUserDetailBinding
import cz.palda97.repoviewer.view.RecyclerViewCosmetics.addDividers
import cz.palda97.repoviewer.viewmodel.userdetail.UserDetailViewModel

/**
 * Activity containing a recycler view with the repository list.
 * User can either navigate to the
 * [RepoDetailActivity][cz.palda97.repoviewer.view.repodetail.RepoDetailActivity].
 */
class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by lazy {
        UserDetailViewModel.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        setupRecyclerView()
        setupTitle()
        checkForNewActivityRequest()
    }

    private fun setupRecyclerView() {
        val adapter = RepositoryAdapter { viewModel.repositoryOnClick(it) }
        binding.repoRecyclerview.adapter = adapter
        binding.repoRecyclerview.addDividers(this)
        viewModel.liveRepositories.observe(this, {
            val repositories = it ?: return@observe
            adapter.updateItemList(repositories)
        })
    }

    private fun setupTitle() {
        supportActionBar?.title = viewModel.title
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