package com.example.casaya

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.casaya.entities.User
import com.example.casaya.repositories.UserRepository
import com.example.casaya.utils.CustomToast
import com.example.casaya.viewmodels.UserLoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var MSG_SUCCESS_LOGIN: String = "Inicio de sesion exitoso"
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var signInImageButton: ImageButton
    private val viewModelUserLogin: UserLoginViewModel = UserLoginViewModel()
    private val repositoryUser: UserRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_bar)

        signInImageButton = findViewById<ImageButton>(R.id.signInImageButton)

        bottomNavView.isVisible = false

        val menu = bottomNavView.menu

        //Si el usuario se ha loggeado, visibilizo todos los botones de BootmNavigationBar
        if (viewModelUserLogin.userIsLoggedIn()) {
            signInImageButton.visibility = View.GONE
            bottomNavView.isVisible = true
            menu.findItem(R.id.publishPropertyFragment).isVisible = true
            menu.findItem(R.id.propertiesListFragment).isVisible = true
            menu.findItem(R.id.containerProfileFragment).isVisible = true

            //Manejo la activacion del botn Dashboard del BottonNavigationBar
            getUserRol { userRol ->
                if (userRol != null) {
                    Log.i("Main Activity Menu_2", "Rol del usuario $userRol")

                    // Mostrar el elemento "Dashboard" cuando el rol de usuario es "ADMIN"
                    if (userRol == "ADMIN") {
                        menu.findItem(R.id.menu_dashboard).isVisible = true
                    }
                }
            }
        }

        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        //Se muestra el CustomToast para el inicio se sesion exitoso
        if (intent?.getBooleanExtra("login_success", false) == true) {
            val customToast = CustomToast(this)
            customToast.show(MSG_SUCCESS_LOGIN, R.drawable.ic_toast_inf)
        }
    }

    override fun onStart() {
        super.onStart()

        signInImageButton.setOnClickListener {
            //Toast.makeText(this, "Iniciando sesion", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("login_success", true)
            startActivity(intent)
            this.finish()
        }
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