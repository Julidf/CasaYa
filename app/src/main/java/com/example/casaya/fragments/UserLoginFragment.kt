package com.example.casaya.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.example.casaya.viewmodels.UserRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserLoginFragment : Fragment() {

    companion object {
        fun newInstance() = UserLoginFragment()
    }

    private val viewModelUserRegister: UserRegisterViewModel by activityViewModels()
    private lateinit var view: View
    private lateinit var welcomeMessage: String


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

        //Inicializa cada una de las referencias de los elementos del formulario
        /*
        initializeViewElements(view)
         */

        val view = inflater.inflate(R.layout.fragment_user_login, container, false)

        emailUserEditText = view.findViewById(R.id.emailUserEditText)
        passwordUserEditText = view.findViewById(R.id.passwordUserEditText)
        buttonLogin = view.findViewById(R.id.buttonLogin)

        firebaseAuth = FirebaseAuth.getInstance()

        buttonLogin.setOnClickListener {
            val email = emailUserEditText.text.toString()
            val password = passwordUserEditText.text.toString()

            signInUser(email, password)
        }

        val textViewRegister = view.findViewById<TextView>(R.id.textViewLogin)
        textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userRegisterFragment)
        }

        return view
    }

    private fun signInUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    // Perform further actions, such as navigating to another fragment or activity
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Login failed
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /*
    override fun onStart() {
        super.onStart()

        //capturo el evento del boton "CREAR MI CUENTA"
        createUserButton.setOnClickListener {

            //Verifico si los datos cargados en el form son validos
            if (formIsValid()) {
                //Envio la informacion del formulario al ViewModel
                submitDataForm()

                viewModelUserRegister.welcomeMessage = welcomeMessage

                val action = UserRegisterFragmentDirections.actionUserRegisterFragmentToUserRegistrationSuccessFragment()
                findNavController().navigate(action)
            }
        }
    }

     */

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
            passwordUserEditText.error = "Por favor, ingrese una contraseña valida, de al menos 5 caracteres"
            isValid = false
        }

        return isValid
    }

    /**
     * Enviar los datos del formulario al ViewModel de la clase UserRegisterViewModel
     */
    /*
    private fun submitDataForm() {
        val email = emailUserEditText.text.toString()
        val password = passwordUserEditText.text.toString()

        viewModelUserRegister.createNewUser(
            email,
            password
        )
    }
     */

    /**
     * Se obtienen todas las referencias de los elementos desde la vista
     */

    /*
    private fun initializeViewElements(view: View) {
        emailUserEditText = view.findViewById(R.id.emailUserEditText)
        passwordUserEditText = view.findViewById(R.id.passwordUserEditText)

        initializeSpinnersElements()
    }
     */

    /**
     * Se inicializan todos los elementos de la clase Spinner con los valores seleccionados
     * desde el formulario
     */

    /*
    private fun initializeSpinnersElements() {
        // Configura el adapter para el spinner de Provincias
        val provinceAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opciones_spinner_province,
            android.R.layout.simple_spinner_item
        )
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceUserSpinner.adapter = provinceAdapter

        // Configura el adapter para el spinner de Barrios
        val districtAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opciones_spinner_barrios,
            android.R.layout.simple_spinner_item
        )
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtUserSpinner.adapter = districtAdapter
    }
     */

}