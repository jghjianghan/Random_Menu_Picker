package com.example.randommenupicker.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    //fragments :
    private lateinit var homeFragment: HomeFragment
    private lateinit var leftDrawerFragment: LeftDrawerFragment
    private lateinit var menuFragment: MenuFragment
    private lateinit var menuDetailFragment: MenuDetailFragment

    private lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeFragment = HomeFragment.newInstance()
        leftDrawerFragment = LeftDrawerFragment.newInstance()
        menuFragment = MenuFragment.newInstance()
        menuDetailFragment = MenuDetailFragment.newInstance()

        setSupportActionBar(binding.toolbar)
        val abdt =  ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.openDrawer, R.string.closeDrawer)
        binding.drawerLayout.addDrawerListener(abdt)
        abdt.syncState()

        fragmentManager = supportFragmentManager
        var ft : FragmentTransaction = fragmentManager.beginTransaction()
        ft.add(R.id.fragment_container, homeFragment)
            .addToBackStack(null)
            .commit()


    }


}