package com.example.randommenupicker.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class MenuDetailAdapter(
    var activity: FragmentActivity,
    var fm : FragmentManager,
    var numOfTabs : Int,

) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getCount(): Int {
        return numOfTabs;
    }

    override fun getItem(position: Int): Fragment {

        when(position) {
            0 -> return MenuDetailDeskripsiFragment()
            1 -> return MenuDetailLangkahFragment()
            else -> return MenuDetailRestoFragment()
        }
    }

}