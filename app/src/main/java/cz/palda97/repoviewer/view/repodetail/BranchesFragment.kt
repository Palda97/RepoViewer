package cz.palda97.repoviewer.view.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.FragmentBranchesBinding
import cz.palda97.repoviewer.view.RecyclerViewCosmetics.addDividers
import cz.palda97.repoviewer.viewmodel.repodetail.RepoDetailViewModel
import cz.palda97.repoviewer.viewmodel.repodetail.RepoDetailViewModel.Companion.msg

/**
 * Fragment containing a recycler view with the branch list.
 */
class BranchesFragment : Fragment() {

    private var _binding: FragmentBranchesBinding? = null
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_branches, container, false)
        setupRecyclerView()
        setupStatusCode()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = BranchAdapter()
        binding.commitsRecyclerview.adapter = adapter
        binding.commitsRecyclerview.addDividers(requireContext())
        viewModel.liveBranches.observe(viewLifecycleOwner, {
            val branches = it ?: return@observe
            adapter.updateItemList(branches)
        })
    }

    private fun setupStatusCode() {
        viewModel.liveBranchStatus.observe(viewLifecycleOwner, {
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
        fun newInstance() = BranchesFragment()
    }
}