package com.example.randommenupicker.view

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.ActivityMainBinding
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    //fragments :
    private lateinit var homeFragment: HomeFragment
    private lateinit var leftDrawerFragment: LeftDrawerFragment
    private lateinit var menuFragment: MenuFragment
    private lateinit var menuDetailFragment: MenuDetailFragment
    private lateinit var menuDetailEditFragment: MenuDetailEditFragment
//    private lateinit var cariFragment: CariFragment

    private lateinit var fragmentManager : FragmentManager
    lateinit var viewModel: MainActivityViewModel
    lateinit var navAdapter: NavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        navAdapter = NavAdapter(this, viewModel, Page.values())

        homeFragment = HomeFragment.newInstance()
        leftDrawerFragment = LeftDrawerFragment.newInstance()
        menuFragment = MenuFragment.newInstance()
        menuDetailFragment = MenuDetailFragment.newInstance()
        menuDetailEditFragment = MenuDetailEditFragment.newInstance()

        this.findViewById<ListView>(R.id.list_nav).adapter = navAdapter

        setSupportActionBar(binding.toolbar)
        val abdt =  ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        )
        binding.drawerLayout.addDrawerListener(abdt)
        abdt.syncState()

        fragmentManager = supportFragmentManager
        var ft : FragmentTransaction = fragmentManager.beginTransaction()
        ft.add(R.id.fragment_container, homeFragment)
            .commit()
    }

    fun changePage (p: Page){
        val ft = supportFragmentManager.beginTransaction()
        when(p){
            Page.HOME->{

            }
            Page.CARI -> TODO()
            Page.MENU -> TODO()
            Page.SETTING -> TODO()
            Page.EXIT -> {
                this.moveTaskToBack(true)
                this.finish()
            }
        }
    }
}