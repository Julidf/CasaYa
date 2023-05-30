package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.casaya.R
import com.example.casaya.viewmodels.UserRegisterViewModel
import com.example.casaya.viewmodels.UserRegistrationSuccessViewModel

class UserRegistrationSuccessFragment : Fragment() {

    companion object {
        fun newInstance() = UserRegistrationSuccessFragment()
    }

    private lateinit var viewModel: UserRegistrationSuccessViewModel
    private val viewModelUserRegister: UserRegisterViewModel by activityViewModels()
    private lateinit var view: View
    private lateinit var messageSuccessTextView: TextView
    private lateinit var continueButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_user_registration_success, container, false)

        messageSuccessTextView = view.findViewById(R.id.messageSuccessTextView)
        continueButton = view.findViewById(R.id.continueButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        messageSuccessTextView.text = viewModelUserRegister.welcomeMessage

        continueButton.setOnClickListener {
            val action = UserRegistrationSuccessFragmentDirections.actionUserRegistrationSuccessFragmentToMainActivity()
            findNavController().navigate(action)
        }
    }

}