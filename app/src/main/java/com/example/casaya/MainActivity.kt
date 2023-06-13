package com.example.casaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.casaya.entities.User
import com.example.casaya.repositories.UserRepository
import com.example.casaya.viewmodels.UserLoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private val viewModelUserLogin: UserLoginViewModel = UserLoginViewModel()
    private val repositoryUser: UserRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_bar)

        val menu = bottomNavView.menu

        getUserRol { userRol ->
            if (userRol != null) {
                Log.i("Main Activity Menu_2", "Rol del usuario $userRol")

                // Mostrar u ocultar el elemento "Dashboard" según el rol de usuario
                if (userRol == "ADMIN") {
                    menu.findItem(R.id.menu_dashboard).isVisible = true
                }
            }
        }

        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    private fun getUserRol(callback: (String?) -> Unit) {
        val userId = viewModelUserLogin.getUserUid()
        val userTask = repositoryUser.getUser(userId)
        userTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    val userRol = user?.getUserRol()
                    callback(userRol)
                } else {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }

}