package cz.palda97.repoviewer.view.repodetail

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import cz.palda97.repoviewer.databinding.ActivityRepoDetailBinding
import cz.palda97.repoviewer.viewmodel.repodetail.RepoDetailViewModel

class RepoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoDetailBinding

    val viewModel by lazy {
        RepoDetailViewModel.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTabs()
        setupTitle()
    }

    private fun setupTabs() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    private fun setupTitle() {
        binding.title.text = viewModel.title
    }
}