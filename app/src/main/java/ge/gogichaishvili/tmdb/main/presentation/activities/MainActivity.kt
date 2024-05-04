package ge.gogichaishvili.tmdb.main.presentation.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.databinding.ActivityMainBinding
import ge.gogichaishvili.tmdb.main.presentation.activities.base.BaseActivity
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity  : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}