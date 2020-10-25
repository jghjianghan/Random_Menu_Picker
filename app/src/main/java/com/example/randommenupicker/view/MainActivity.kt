package com.example.randommenupicker.view

import android.os.Bundle
import android.view.WindowManager
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
    private lateinit var cariFragment: CariFragment

    private lateinit var fragmentManager : FragmentManager
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.loadMenu(this)

        homeFragment = HomeFragment.newInstance()
        leftDrawerFragment = LeftDrawerFragment.newInstance()
        cariFragment = CariFragment.newInstance()
        menuFragment = MenuFragment.newInstance()
        menuDetailFragment = MenuDetailFragment.newInstance()
        menuDetailEditFragment = MenuDetailEditFragment.newInstance()

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

        viewModel.getPage().observe(this, {
            println("changed")
            this.changePage(it)
        })

        viewModel.getToolbarTitle().observe(this, {
            changeToolbarTitle(it)
        })
    }

    private fun changePage(p: Page){
        val ft = supportFragmentManager.beginTransaction()

        when(p){
            Page.HOME -> {
                ft.replace(R.id.fragment_container, homeFragment)
            }
            Page.CARI -> {
                ft.replace(R.id.fragment_container, cariFragment)
            }
            Page.LIST_MENU -> {
                viewModel.setMenuTitle("")
                ft.replace(R.id.fragment_container, menuFragment)
            }
            Page.LIST_MENU_DARI_CARI -> {
                println("list menu dari cari")
                val lastHist = viewModel.getLastHistory()
                viewModel.setMenuTitle(getString(R.string.search_result_label) + "\"${lastHist?.keyword}\"")
                ft.replace(R.id.fragment_container, menuFragment).addToBackStack(null)
            }
            Page.MENU_DETAIL -> {
                ft.replace(R.id.fragment_container, menuDetailFragment)
            }
            Page.EDIT_MENU -> {
                ft.replace(R.id.fragment_container, menuDetailEditFragment)
            }
            Page.SETTING -> TODO()
            Page.EXIT -> {
                this.moveTaskToBack(true)
                this.finish()
            }
        }
        ft.commit()
        binding.drawerLayout.closeDrawers()
    }

    private fun changeToolbarTitle(title: String){
        supportActionBar?.title = title
    }

    override fun onDestroy() {
        viewModel.writeAllMenu(this)
        super.onDestroy()
    }
}