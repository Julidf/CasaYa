package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.casaya.R
import com.example.casaya.viewmodels.UserRegistrationSuccessViewModel

class UserRegistrationSuccessFragment : Fragment() {

    companion object {
        fun newInstance() = UserRegistrationSuccessFragment()
    }

    private lateinit var viewModel: UserRegistrationSuccessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_registration_success, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserRegistrationSuccessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}