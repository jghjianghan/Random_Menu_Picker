package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentMenuDetailBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import com.google.android.material.tabs.TabLayout

class MenuDetailFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private lateinit var binding : FragmentMenuDetailBinding
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var viewPager : ViewPager
    private lateinit var tabBar : TabLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu_detail, container, false)
        viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        tabBar = view.findViewById<TabLayout>(R.id.tab_bar)
        var pagerAdapter = MenuDetailAdapter(
            requireActivity(),
            fragmentManager as FragmentManager,
            tabBar.tabCount
        )
        viewPager.adapter = pagerAdapter
        tabBar.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        return view
    }


    companion object {
        fun newInstance() : MenuDetailFragment{
            var fragment = MenuDetailFragment()
            return fragment
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null){
            viewPager.currentItem = tab.position
        }

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }


}