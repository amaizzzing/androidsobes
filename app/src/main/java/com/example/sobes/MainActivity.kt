package com.example.sobes

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.sobes.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    lateinit var navController: NavController

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                    initNavigation()
                    navController.navigate(R.id.navigation_map)
                } else -> {
                    Toast.makeText(this, "ERROR PERMISSIONS", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding?.root)

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initNavigation() {
        val navView: BottomNavigationView? = activityMainBinding?.bottomNavView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)
        navController.graph = navGraph

        activityMainBinding?.let { binding ->
            navView?.setupWithNavController(navController)

            binding.bottomNavView.setOnItemSelectedListener { menuItem ->
                if (menuItem.itemId != navController.currentDestination?.id) {
                    navController.navigate(
                        menuItem.itemId,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.mobile_navigation, false).build()
                    )
                }
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityMainBinding = null
    }
}