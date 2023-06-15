package com.example.casaya

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_bar)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }
}