package cz.palda97.repoviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ActivityMainBinding
import cz.palda97.repoviewer.viewmodel.MainScreenViewModel

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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}