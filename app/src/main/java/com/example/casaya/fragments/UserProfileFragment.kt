package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.casaya.R
import com.example.casaya.viewmodels.PropertiesListViewModel
import com.example.casaya.viewmodels.UserProfileViewModel

class UserProfileFragment : Fragment() {


    lateinit var v: View
    lateinit var btnNavigate : Button
    private val viewModelUserProfile: UserProfileViewModel by activityViewModels()

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_profile, container, false)

        return v
    }

   // override fun onStart() {
     //   super.onStart()
       // btnNavigate.setOnClickListener{
         //   val action = UserProfileFragmentDirections.
       // }

    //}




}