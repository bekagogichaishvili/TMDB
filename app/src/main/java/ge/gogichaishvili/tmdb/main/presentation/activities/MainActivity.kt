package ge.gogichaishvili.tmdb.main.presentation.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.dmoral.toasty.Toasty
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.databinding.ActivityMainBinding
import ge.gogichaishvili.tmdb.main.presentation.activities.base.BaseActivity
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    private var isFirstStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val container = findViewById<View>(android.R.id.content)
        container.viewTreeObserver.addOnPreDrawListener {
            true
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        setupNavigation(bottomNavigationView)

    }

    private fun setupNavigation(bottomNavigationView: BottomNavigationView) {
        val navHostFragment = NavHostFragment.create(R.navigation.navigation)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, navHostFragment)
            setPrimaryNavigationFragment(navHostFragment)
            commit()
        }

        supportFragmentManager.executePendingTransactions()

        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        isConnectionLiveData.observe(this, Observer { isConnected ->
            if (!isFirstStart) {
                if (isConnected) {
                    Toasty.success(this, R.string.connection_success, Toast.LENGTH_SHORT, true)
                        .show()
                } else {
                    Toasty.error(this, R.string.connection_loss, Toast.LENGTH_SHORT, true).show()
                }
            } else {
                isFirstStart = false
            }
        })
    }
}
