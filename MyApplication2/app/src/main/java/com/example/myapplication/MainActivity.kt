package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fabAddSong.setOnClickListener {
            navController.navigate(R.id.action_songListFragment_to_addSongFragment)
        }

        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_statistics -> {
                navController.navigate(R.id.practiceStatisticsFragment)
                true
            }
            R.id.action_profile -> {
                navController.navigate(R.id.profileFragment)
                true
            }
            R.id.action_backup -> {
                navController.navigate(R.id.backupRestoreFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEND || intent.action == Intent.ACTION_SEND_MULTIPLE) {
            val uris = mutableListOf<Uri>()
            if (intent.action == Intent.ACTION_SEND) {
                intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
                    uris.add(it as Uri)
                }
            } else {
                intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
                    uris.addAll(it.filterIsInstance<Uri>())
                }
            }
            
            if (uris.size == 1) {
                val uri = uris[0]
                val contentResolver = contentResolver
                val type = contentResolver.getType(uri) ?: ""
                
                if (type == "application/zip" || uri.path?.endsWith(".zip") == true) {
                    val bundle = bundleOf("zipUri" to uri)
                    navController.navigate(R.id.importPackageFragment, bundle)
                    return
                }
            }

            if (uris.isNotEmpty()) {
                val bundle = bundleOf("uris" to uris.toTypedArray())
                navController.navigate(R.id.importSelectionFragment, bundle)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
