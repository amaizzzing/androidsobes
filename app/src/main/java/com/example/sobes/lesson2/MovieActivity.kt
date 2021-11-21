package com.example.sobes.lesson2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sobes.R
import com.example.sobes.databinding.ActivityMovieBinding

class MovieActivity: AppCompatActivity() {
    lateinit var navController: NavController
    private var activityMovieBinding: ActivityMovieBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieBinding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(activityMovieBinding?.root)

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_movie) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.movie_navigation)
        navController.graph = navGraph

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.navigate(R.id.navigation_top_rated)
    }
}