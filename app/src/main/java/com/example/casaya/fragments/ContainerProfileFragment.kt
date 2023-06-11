package com.example.casaya.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.casaya.R
import com.example.casaya.adapters.ViewPagerAdapter
import com.example.casaya.viewmodels.ContainerProfileViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ContainerProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ContainerProfileFragment()
    }

    private lateinit var viewModel: ContainerProfileViewModel
    lateinit var v: View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_container_profile, container, false)

        viewPager = v.findViewById(R.id.view_pager)
        tabLayout = v.findViewById(R.id.tab_layout)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewPager.adapter = ViewPagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Mi Perfil"
                1 -> tab.text = "Mis Propiedades"
                //else -> tab.text = "undefined"
            }
        }.attach()
    }
}