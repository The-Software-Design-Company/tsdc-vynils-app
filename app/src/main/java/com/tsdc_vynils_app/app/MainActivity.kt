package com.tsdc_vynils_app.app

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tsdc_vynils_app.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //cambiando estilos y colores a la barra de navegación
        val navView: BottomNavigationView = binding.navView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setBackgroundColor(resources.getColor(R.color.background_bar))
        val iconColors = resources.getColorStateList(R.color.bottom_nav_colors)
        bottomNavigationView.itemIconTintList = iconColors
        val textColors = resources.getColorStateList(R.color.bottom_nav_text_colors)
        bottomNavigationView.itemTextColor = textColors
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))




        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_musician, R.id.navigation_collectors
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}