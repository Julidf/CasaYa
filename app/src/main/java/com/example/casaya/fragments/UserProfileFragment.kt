package com.example.casaya.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.casaya.R
import com.example.casaya.repositories.UserRepository
import com.example.casaya.viewmodels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileFragment : Fragment() {


    private lateinit var userViewModel: UserProfileViewModel

    lateinit var v: View
    private lateinit var userName: TextView
    private lateinit var userPhone: TextView
    private lateinit var userDireccion: TextView
    private lateinit var userMail: TextView
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
        initializeView(v)
        userViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        return v
    }

    private fun initializeView(view: View) {
        userName = view.findViewById(R.id.userNameTextView)
        userPhone = view.findViewById(R.id.userPhoneTextView)
        userDireccion = view.findViewById(R.id.userDireccionTextView)
        userMail = view.findViewById(R.id.userEmailTextView)

    }

    override  fun onStart() {
        super.onStart()


            if (userRef != null) {
                loadDataIntoView(userRef)
            }


    }


    private  fun loadDataIntoView(userRef: String?) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                val usuario = userRef?.let { userViewModel.obtenerUsuarioPorId(userRef) }
                if (usuario != null) {
                    // Realiza las operaciones necesarias con el objeto de usuario
                    // usuario.displayName, usuario.email, etc.
                    userName.text = usuario.getName()
                    userPhone.text = usuario.getPhone()
                    userDireccion.text = usuario.getAddress()?.getStreet() + " " + usuario.getAddress()?.getHeight() ?: "Direccion no encontrada"
                    userMail.text = usuario.getEmail()
                } else {
                    Log.d("UserProfileFragment", "El usuario no existe")
                }
            }
        }
        }


    }

