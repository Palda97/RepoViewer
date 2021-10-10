package cz.palda97.repoviewer.view.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.FragmentBranchesBinding

class BranchesFragment : Fragment() {

    private var _binding: FragmentBranchesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_branches, container, false)
        return binding.root
    }

    companion object {

        fun newInstance() = BranchesFragment()
    }
}