package cz.palda97.repoviewer.view.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.FragmentRecentCommitsBinding
import cz.palda97.repoviewer.view.RecyclerViewCosmetics.addDividers
import cz.palda97.repoviewer.viewmodel.repodetail.RepoDetailViewModel
import cz.palda97.repoviewer.viewmodel.repodetail.RepoDetailViewModel.Companion.msg

/**
 * Fragment containing a recycler view with the commit list.
 */
class RecentCommitsFragment : Fragment() {

    private var _binding: FragmentRecentCommitsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by lazy {
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
        val adapter = CommitAdapter()
        binding.commitsRecyclerview.adapter = adapter
        binding.commitsRecyclerview.addDividers(requireContext())
        viewModel.liveCommits.observe(viewLifecycleOwner, {
            val commits = it ?: return@observe
            adapter.updateItemList(commits)
        })
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

        /**
         * Creates an instance of this fragment.
         */
        fun newInstance() = RecentCommitsFragment()
    }
}