package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.casaya.R
import com.example.casaya.viewmodels.LandingPageViewModel

class LandingPageFragment : Fragment() {

    companion object {
        fun newInstance() = LandingPageFragment()
    }

    private lateinit var viewModelLandingPage: LandingPageViewModel
    private lateinit var signInButton: Button
    private lateinit var explorePropertiesButton: Button
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_landing_page, container, false)

        signInButton = view.findViewById(R.id.signInButton)
        explorePropertiesButton = view. findViewById(R.id.explorePropertiesButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        signInButton.setOnClickListener {
            val action = LandingPageFragmentDirections.actionLandingPageFragmentToUserLoginFragment()
            findNavController().navigate(action)
        }

        explorePropertiesButton.setOnClickListener {
            val action = LandingPageFragmentDirections.actionLandingPageFragmentToMainActivity()
            findNavController().navigate(action)
        }
    }

}