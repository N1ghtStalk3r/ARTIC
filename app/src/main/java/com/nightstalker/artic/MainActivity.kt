package com.nightstalker.artic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nightstalker.artic.databinding.ActivityMainBinding
import com.nightstalker.artic.features.artwork.presentation.ui.ArtworkViewModel
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import com.nightstalker.artic.features.exhibition.presentation.ui.ExhibitionsViewModel
import com.nightstalker.artic.features.ticket.presentation.ui.TicketsViewModel
import com.nightstalker.artic.network.ApiConstants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val artworksViewModel by viewModel<ArtworkViewModel>()
    private val audioViewModel by viewModel<AudioViewModel>()
    private val exhibitionsViewModel by viewModel<ExhibitionsViewModel>()
    private val ticketsViewModel by viewModel<TicketsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botNavView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.artworksListFragment,
                R.id.exhibitionsListFragment,
                R.id.ticketsListFragment,
                R.id.audioLookupFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        botNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp() || super.onSupportNavigateUp()

// ?????????????????????? ?????????????? ?? ?????????????????? Google
    fun addCalendarEvent(params: Map<String, String>) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = ApiConstants.EVENT_CALENDAR_TYPE

        params.forEach {
            when (it.key) {
                // ???????????? ???????????? ????????????????, Long ??????????????????
                ApiConstants.EVENT_BEGIN, ApiConstants.EVENT_END -> intent.putExtra(it.key, it.value.toLong())
                // Boolean ?????????????????? - "?????????????? ???????????? ???????? ????????"
                ApiConstants.EVENT_RULE -> intent.putExtra(it.key, it.value == "true")
                // String ??????????????????
                else -> intent.putExtra(it.key, it.value)
            }
        }
        startActivity(intent)
    }

}