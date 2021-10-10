package cz.palda97.repoviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ActivityUserDetailBinding
import cz.palda97.repoviewer.viewmodel.UserDetailViewModel

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
    }

    private fun setupRecyclerView() {
        val adapter = RepositoryRecyclerAdapter { TODO() }
        binding.repoRecyclerview.adapter = adapter
        viewModel.liveRepositories.observe(this, {
            val repositories = it ?: return@observe
            adapter.updateRepositoryList(repositories)
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}