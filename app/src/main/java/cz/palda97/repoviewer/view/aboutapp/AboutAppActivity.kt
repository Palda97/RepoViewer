package cz.palda97.repoviewer.view.aboutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.palda97.repoviewer.databinding.ActivityAboutAppBinding
import cz.palda97.repoviewer.viewmodel.aboutapp.AboutAppViewModel

/**
 * Activity for displaying some info about author and the application.
 */
class AboutAppActivity : AppCompatActivity() {

    private var _binding: ActivityAboutAppBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by lazy {
        AboutAppViewModel.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAboutText()
        setupShowSourceButton()
    }

    private fun setupAboutText() {
        binding.textView.text = viewModel.aboutText
    }

    private fun setupShowSourceButton() {
        binding.showSourceButton.setOnClickListener {
            viewModel.showSourceButton(this)
        }
    }
}