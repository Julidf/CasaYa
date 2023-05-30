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
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.casaya.LoginActivity
import com.example.casaya.R
import com.example.casaya.viewmodels.PropertiesListViewModel
import com.example.casaya.viewmodels.UserRegisterViewModel

class UserRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = UserRegisterFragment()
    }

    private val viewModelUserRegister: UserRegisterViewModel by activityViewModels()
    private lateinit var view: View
    private lateinit var welcomeMessage: String


    /**
     * Elementos del formulario
     */
    private lateinit var userNameEditText: EditText
    private lateinit var userLastnameEditText: EditText
    private lateinit var emailUserEditText: EditText
    private lateinit var passwordUserEditText: EditText
    private lateinit var confirmationPasswordUserEditText: EditText
    private lateinit var phoneUserEditText: EditText

    //Datos del Domicilio
    private lateinit var provinceUserSpinner: Spinner
    private lateinit var districtUserSpinner: Spinner
    private lateinit var streetUserEditText: EditText
    private lateinit var heightUserEditText: EditText
    private lateinit var postalCodeUserEditText: EditText
    private lateinit var textViewLogin: TextView
    private lateinit var createUserButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_user_register, container, false)

        //Inicializa cada una de las referencias de los elementos del formulario
        initializeViewElements(view)

        return view
    }

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

        val textViewLogin = view.findViewById<TextView>(R.id.textViewLogin)
        textViewLogin.setOnClickListener {
            findNavController().navigate(R.id.action_userRegisterFragment_to_userLoginFragment)
        }
    }

    /**
     * Valida si los campos del fomrulario han sido completados correctamente
     */
    private fun formIsValid(): Boolean {
        var isValid = true

        val userName = userNameEditText.text.toString().trim()
        val userLastname = userLastnameEditText.text.toString().trim()
        val emailUser = emailUserEditText.text.toString().trim()
        val passwordUser = passwordUserEditText.text.toString().trim()
        val confirmationPasswordUser = confirmationPasswordUserEditText.text.toString().trim()
        val streetUser = streetUserEditText.text.toString().trim()
        val heightUser = heightUserEditText.text.toString().trim()
        val postalCodeUser = postalCodeUserEditText.text.toString().trim()

        /**
         * Valida el Campo Nombre del usuario
         */
        if (userName.isEmpty()) {
            userNameEditText.error = "Por favor, ingrese su nombre"
            isValid = false
        }

        /**
         * Valida el Campo Apellido del usuario
         */
        if (userLastname.isEmpty()) {
            userLastnameEditText.error = "Por favor, ingrese su apellido"
            isValid = false
        }

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

        /**
         * Valida el Campo Contraseña de confirmacion
         */
        if ((confirmationPasswordUser != passwordUser) || confirmationPasswordUser.isEmpty()) {
            confirmationPasswordUserEditText.error = "La contraseña ingresada con coincide"
            isValid = false
        }

        /**
         * Valida el Campo Nombre de la calle
         */
        if (streetUser.isEmpty()) {
            streetUserEditText.error = "r favor, complete el nombre de la calle de la direccion de la propiedad"
            isValid = false
        }

        /**
         * Valida el Campo Altura de la calle
         */
        if (heightUser.isEmpty() || heightUser.toInt() <= 0) {
            heightUserEditText.error = "Por favor, complete y/o agregue un valor valido para la altura de la calle"
            isValid = false
        }

        /**
         * Valida el Campo Codigo Postal
         */
        if (postalCodeUser.isEmpty()) {
            postalCodeUserEditText.error = "Por favor, complete el codigo postal de la ubicacion de la propiedad"
            isValid = false
        }

        return isValid
    }

    /**
     * Enviar los datos del formulario al ViewModel de la clase UserRegisterViewModel
     */
    private fun submitDataForm() {
        val name = userNameEditText.text.toString()
        val lastname = userLastnameEditText.text.toString()
        val email = emailUserEditText.text.toString()
        val password = confirmationPasswordUserEditText.text.toString()
        val phoneNumber = phoneUserEditText.text.toString()
        val province = provinceUserSpinner.selectedItem.toString()
        val district = districtUserSpinner.selectedItem.toString()
        val street = streetUserEditText.text.toString()
        val height = heightUserEditText.text.toString().toInt()
        val postalCode = postalCodeUserEditText.text.toString()

        //Seteo el mensaje de bienvenida al completarse el registro
        setWelcomeMessage(name, lastname)

        viewModelUserRegister.createNewUser(
            name,
            lastname,
            email,
            password,
            phoneNumber,
            province,
            district,
            street,
            height,
            postalCode,
        )
    }

    /**
     * Metodo que setea el mensaje de bienvenida al completarse el registro
     */
    private fun setWelcomeMessage(name: String, lastname: String) {
        welcomeMessage = "Bienvenido $name $lastname, tu registro se ha realizado exitosamente."
    }

    /**
     * Se obtienen todas las referencias de los elementos desde la vista
     */
    private fun initializeViewElements(view: View) {
        userNameEditText = view.findViewById(R.id.userNameEditText)
        userLastnameEditText = view.findViewById(R.id.userLastnameEditText)
        emailUserEditText = view.findViewById(R.id.emailUserEditText)
        passwordUserEditText = view.findViewById(R.id.passwordUserEditText)
        confirmationPasswordUserEditText = view.findViewById(R.id.confirmationPasswordUserEditText)
        phoneUserEditText = view.findViewById(R.id.phoneUserEditText)
        provinceUserSpinner = view.findViewById(R.id.provinceUserSpinner)
        districtUserSpinner = view.findViewById(R.id.districtUserSpinner)
        streetUserEditText = view.findViewById(R.id.streetUserEditText)
        heightUserEditText = view.findViewById(R.id.heightUserEditText)
        postalCodeUserEditText = view.findViewById(R.id.postalCodeUserEditText)
        createUserButton = view.findViewById(R.id.createUserButton)

        initializeSpinnersElements()
    }

    /**
     * Se inicializan todos los elementos de la clase Spinner con los valores seleccionados
     * desde el formulario
     */
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

}