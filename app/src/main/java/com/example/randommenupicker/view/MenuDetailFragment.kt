package com.example.randommenupicker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.randommenupicker.databinding.FragmentMenuDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MenuDetailFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private lateinit var binding : FragmentMenuDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        var pagerAdapter = MenuDetailAdapter(fragmentManager as FragmentManager, binding.tabBar.tabCount)
        binding.viewPager.adapter = pagerAdapter
        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailFragment{
            var fragment = MenuDetailFragment()
            return fragment
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        var tab2 = tab as TabLayout.Tab
        binding.viewPager.currentItem = tab2.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }


}