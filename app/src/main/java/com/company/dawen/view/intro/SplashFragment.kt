package com.company.dawen.view.intro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.company.dawen.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNotesList()
        }, 3000)
    }

    private fun navigateToNotesList() {
        val navController = NavHostFragment.findNavController(this)
        val action =
            SplashFragmentDirections.actionSplashFragmentToNotesListFragment()
        navController.navigate(action)
    }

}


//private val tag = "SplashActivity"
//
//private lateinit var appTheme: AppTheme
//
//// Handle night mode change
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//        appTheme = AppTheme(this@SplashActivity)
//        initTheme()

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        lifecycleScope.launch {
//            when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//                Configuration.UI_MODE_NIGHT_NO -> {
//                    appTheme.setDarkModeEnabled(false)
//                } // Night mode is not active, we're using the light theme
//                Configuration.UI_MODE_NIGHT_YES -> {
//                    appTheme.setDarkModeEnabled(true)
//                } // Night mode is active, we're using dark theme
//            }
////            initTheme()
//        }
//    }


//    private fun initTheme() {
//
//        lifecycleScope.launch {
//            try {
//                appTheme.isDarkModeEnabled.collect { isDarkModeEnabled ->
//                    AppCompatDelegate.setDefaultNightMode(
//                        if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES
//                        else AppCompatDelegate.MODE_NIGHT_NO
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e(tag, "Error while changing theme ${e.message}")
//            }
//        }
//    }