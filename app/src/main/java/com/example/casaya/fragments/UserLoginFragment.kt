package com.example.casaya.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.casaya.MainActivity
import com.example.casaya.R
import com.example.casaya.viewmodels.PropertiesListViewModel
import com.example.casaya.viewmodels.UserLoginViewModel
import com.example.casaya.viewmodels.UserRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserLoginFragment : Fragment() {

    companion object {
        fun newInstance() = UserLoginFragment()
    }

    private val viewModelUserLogin: UserLoginViewModel = UserLoginViewModel()
    private lateinit var view: View
    private var MSG_SUCCESS_LOGIN: String = "Inicio de sesion exitoso"
    private var MSG_FAILURE_LOGIN: String = "Inicio de sesion fallido, revisa tu email o contraseña"



    /**
     * Elementos del formulario
     */
    private lateinit var emailUserEditText: EditText
    private lateinit var passwordUserEditText: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_user_login, container, false)
        val view = inflater.inflate(R.layout.fragment_user_login, container, false)

        textViewRegister = view.findViewById(R.id.textViewLogin)
        emailUserEditText = view.findViewById(R.id.emailUserEditText)
        passwordUserEditText = view.findViewById(R.id.passwordUserEditText)
        buttonLogin = view.findViewById(R.id.buttonLogin)

        firebaseAuth = FirebaseAuth.getInstance()

        return view
    }

    private fun signInUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    if (user != null) {
                        //viewModelUserLogin.currentUser = user
                        Log.d("Login1 User", "Usuario logeado: ID ${user.uid} / ${user.email}")
                    }
                    // Perform further actions, such as navigating to another fragment or activity
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    Toast.makeText(requireContext(), MSG_SUCCESS_LOGIN, Toast.LENGTH_SHORT).show()
                } else {
                    // Login failed
                    Toast.makeText(requireContext(), MSG_FAILURE_LOGIN, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        //capturo el evento del boton "CREAR MI CUENTA"
        buttonLogin.setOnClickListener {

            //Verifico si los datos cargados en el form son validos
            if (formIsValid()) {
                val email = emailUserEditText.text.toString()
                val password = passwordUserEditText.text.toString()

                signInUser(email, password)
            }
        }

        //Accion para navegar al form de User Register
        textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userRegisterFragment)
        }
    }

    /**
     * Valida si los campos del fomrulario han sido completados correctamente
     */
    private fun formIsValid(): Boolean {
        var isValid = true

        val emailUser = emailUserEditText.text.toString().trim()
        val passwordUser = passwordUserEditText.text.toString().trim()

        /**
         * Valida el Campo Correo Electronico
         */
        if (emailUser.isEmpty()) {
            emailUserEditText.error = "Por favor, ingrese su correo electronico"
            isValid = false
        }

        /**
         * Valida el Campo Contraseña
         */
        if (passwordUser.isEmpty() || passwordUser.length < 5) {
            passwordUserEditText.error = "Por favor, ingrese su contraseña"
            isValid = false
        }

        return isValid
    }

}