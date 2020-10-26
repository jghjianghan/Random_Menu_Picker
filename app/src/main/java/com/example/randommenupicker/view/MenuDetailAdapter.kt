package com.example.randommenupicker.view

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MenuDetailAdapter(
    fm:FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    var fragments = ArrayList<Fragment>()
    var fragmentTitle = ArrayList<String>()

    fun addFragment(fragment : Fragment , title : String) {
        fragments.add(fragment)
        fragmentTitle.add(title)
    }

    override fun getCount(): Int {
        return fragments.size;
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle.get(position)
    }
}