package com.alpaca.telalogin.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alpaca.telalogin.databinding.ActivityMainBinding
import com.alpaca.telalogin.util.SalvarImagem
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val bottomNavigationView: BottomNavigationView by lazy { binding.bottomNavigationView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraNavigation()
    }

    private fun configuraNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(bottomNavigationView.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun abreIntentCompartilhar(bitmap: Bitmap) {
        val uriImagem = SalvarImagem(this).obtemUriImagem(bitmap)
        val intentShare = Intent()
        intentShare.action = Intent.ACTION_SEND
        intentShare.putExtra(Intent.EXTRA_STREAM, uriImagem)
        intentShare.type = "image/png"
        intentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intentShare, "Compartilhar"))
    }
}