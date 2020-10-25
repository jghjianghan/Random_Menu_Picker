package com.example.randommenupicker.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
    private lateinit var settingFragment : SettingFragment

    private lateinit var fragmentManager : FragmentManager
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.loadAllData(this)

        homeFragment = HomeFragment.newInstance()
        leftDrawerFragment = LeftDrawerFragment.newInstance()
        cariFragment = CariFragment.newInstance()
        menuFragment = MenuFragment.newInstance()
        menuDetailFragment = MenuDetailFragment.newInstance()
        menuDetailEditFragment = MenuDetailEditFragment.newInstance()
        settingFragment = SettingFragment.newInstance()

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

        viewModel.getWriteMenuFlag().observe(this, {
            if (it){
                viewModel.writeAllMenu(this)
            }
        })
        viewModel.getWriteHistoryFlag().observe(this, {
            if (it){
                viewModel.writeAllHistory(this)
            }
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
                viewModel.setSearchBarKeyword("")
                ft.replace(R.id.fragment_container, menuFragment)
            }
            Page.LIST_MENU_DARI_CARI -> {
                println("list menu dari cari")
                val lastHist = viewModel.getLastHistory()
                viewModel.setMenuTitle(getString(R.string.search_result_label) + "\"${viewModel.getSearchBarKeyword().value}\"")
                ft.replace(R.id.fragment_container, menuFragment).addToBackStack(null)
            }
            Page.MENU_DETAIL -> {
                ft.replace(R.id.fragment_container, menuDetailFragment).addToBackStack(null)
            }
            Page.EDIT_MENU -> {
                ft.replace(R.id.fragment_container, menuDetailEditFragment).addToBackStack(null)
            }
            Page.SETTING -> {
                ft.replace(R.id.fragment_container, settingFragment)
            }
            Page.EXIT -> {
                this.moveTaskToBack(true)
                this.finish()
            }
            Page.POP_PAGE -> {
                supportFragmentManager.popBackStackImmediate()
            }
        }
        ft.commit()
        binding.drawerLayout.closeDrawers()
    }

    private fun changeToolbarTitle(title: String){
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}