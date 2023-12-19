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
