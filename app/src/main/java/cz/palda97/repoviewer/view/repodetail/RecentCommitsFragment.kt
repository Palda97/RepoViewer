package cz.palda97.repoviewer.view.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.FragmentRecentCommitsBinding
import cz.palda97.repoviewer.model.repository.RepoRepository
import cz.palda97.repoviewer.view.RecyclerViewCosmetics.addDividers
import cz.palda97.repoviewer.viewmodel.repodetail.RepoDetailViewModel

class RecentCommitsFragment : Fragment() {

    private var _binding: FragmentRecentCommitsBinding? = null
    private val binding
        get() = _binding!!

    val viewModel by lazy {
        RepoDetailViewModel.getInstance(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_commits, container, false)
        setupRecyclerView()
        setupStatusCode()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = CommitRecyclerAdapter()
        binding.commitsRecyclerview.adapter = adapter
        binding.commitsRecyclerview.addDividers(requireContext())
        viewModel.liveCommits.observe(viewLifecycleOwner, {
            val commits = it ?: return@observe
            adapter.updateCommitList(commits)
        })
    }

    private val RepoRepository.StatusCode.msg
        get() = when (this) {
            RepoRepository.StatusCode.NOT_FOUND -> getString(R.string.user_has_not_been_found)
            RepoRepository.StatusCode.CONNECTION_PROBLEM -> getString(R.string.there_is_a_connection_problem)
            RepoRepository.StatusCode.PARSING_ERROR -> getString(R.string.there_is_a_parsing_problem)
            RepoRepository.StatusCode.LOADING -> getString(R.string.internal_error)
            RepoRepository.StatusCode.OK -> getString(R.string.internal_error)
        }

    private fun setupStatusCode() {
        viewModel.liveCommitStatus.observe(viewLifecycleOwner, {
            val status = it ?: return@observe
            binding.errorText = status.msg
            binding.status = status
            binding.executePendingBindings()
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        fun newInstance() = RecentCommitsFragment()
    }
}