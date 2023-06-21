package com.example.casaya.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.casaya.R
import com.example.casaya.repositories.UserRepository
import com.example.casaya.utils.CustomToast
import com.example.casaya.viewmodels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileFragment : Fragment() {


    private lateinit var userViewModel: UserProfileViewModel
    private lateinit var customToast: CustomToast

    lateinit var v: View
    private lateinit var userName: TextView
    private lateinit var userPhone: TextView
    private lateinit var userStreet: TextView
    private lateinit var userHeight: TextView
    private lateinit var userMail: TextView
    private lateinit var buttonSelectImage: ImageView
    private lateinit var circleImageView: CircleImageView
    private lateinit var signOutButton: Button
    private lateinit var btnUpdateDatos: Button
    private val firebaseAuth = FirebaseAuth.getInstance()
    var firebaseUser = firebaseAuth.currentUser
    val userRef = firebaseUser?.uid


    companion object {
        fun newInstance() = UserProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_profile, container, false)

        customToast = CustomToast(requireContext())

        initializeView(v)
        userViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        userMail.isClickable = false
        userMail.isLongClickable = false

        return v
    }

    private fun initializeView(view: View) {
        userName = view.findViewById(R.id.userNameTextView)
        userPhone = view.findViewById(R.id.userPhoneTextView)
        userStreet = view.findViewById(R.id.userDireccionTextView)
        userHeight = view.findViewById(R.id.userDireccionTextView2)
        userMail = view.findViewById(R.id.userEmailTextView)
        buttonSelectImage = view.findViewById(R.id.buttonSelectImage)
        circleImageView = view.findViewById(R.id.circleImageView)
        signOutButton = view.findViewById(R.id.signOutButton)
        btnUpdateDatos = view.findViewById(R.id.btnUpdateDatos)
    }

    override fun onStart() {
        super.onStart()

        if (userRef != null) {
            loadDataIntoView(userRef)
        }

        buttonSelectImage.setOnClickListener {
            TedImagePicker.with(requireContext())
                .start { uri ->
                    userViewModel.setUserImage(uri, requireContext())
                    Log.i("Fragment User Profile", "Imagen a mostrar: $uri")
                    Glide.with(this)
                        .load(uri)
                        .into(circleImageView)

                    customToast.show(
                        "La imagen de perfil ha sido actualizada!",
                        R.drawable.ic_toast_inf
                    )
                }
        }

        //Boton para Cerrar Sesion
        signOutButton.setOnClickListener {
            Log.i("Sign Out Fragment", "Iniciando el cierre de sesion: boton presionado")
            userViewModel.signOutUser()
            findNavController().navigate(R.id.action_containerProfileFragment_to_loginActivity)
        }
        btnUpdateDatos.setOnClickListener {
            val newPhone = userPhone.text.toString()
            val newStreet = userStreet.text.toString()
            val newHeight = userHeight.text.toString()
            val newName   = userName.text.toString()
            if (newPhone.isEmpty() || newStreet.isEmpty() ||  newHeight.isEmpty() || newName.isEmpty()){
                customToast.show(
                    "Los datos no pueden estar vacios",
                    R.drawable.ic_toast_inf
                )
            }else{
                userViewModel.setUserPhone(newPhone)
                userViewModel.setUserDireccion(newStreet, newHeight.toInt())
                userViewModel.setUserName(newName)
                customToast.show(
                    "Datos actualizados correctamente",
                    R.drawable.ic_toast_inf
                )
            }


        }
    }


    private fun loadDataIntoView(userRef: String?) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                val usuario = userRef?.let { userViewModel.obtenerUsuarioPorId(userRef) }
                if (usuario != null) {
                    // Realiza las operaciones necesarias con el objeto de usuario
                    // usuario.displayName, usuario.email, etc.
                    userName.text = usuario.getName()
                    userPhone.text = usuario.getPhone()
                    userStreet.text = usuario.getAddress()?.getStreet()
                    userHeight.text = usuario.getAddress()?.getHeight().toString()
                    userMail.text = usuario.getEmail()

                    val userImageRef = usuario.getUserImageRef()
                    if (userImageRef.isNotEmpty()) {
                        loadImageFromDB(userImageRef)
                    }

                } else {
                    Log.d("UserProfileFragment", "El usuario no existe")
                }
            }
        }
    }

    private fun loadImageFromDB(userImageRef: String) {
        Glide.with(this@UserProfileFragment)
            .load(userImageRef.toUri())
            .into(circleImageView)
    }


}

