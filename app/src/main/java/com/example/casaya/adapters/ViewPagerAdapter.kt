package com.example.casaya.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.casaya.fragments.UserProfileFragment
import com.example.casaya.fragments.UserPropertiesFragment

class ViewPagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    companion object {
        private const val TAB_COUNT = 2
    }

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> UserProfileFragment()
            1 -> UserPropertiesFragment()

            else -> UserProfileFragment()
        }
    }
}