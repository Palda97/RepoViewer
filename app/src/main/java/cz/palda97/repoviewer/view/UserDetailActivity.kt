package cz.palda97.repoviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cz.palda97.repoviewer.R
import cz.palda97.repoviewer.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}