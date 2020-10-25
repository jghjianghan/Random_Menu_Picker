package com.example.randommenupicker.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentMenuDetailBinding
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MenuDetailFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var viewPager : ViewPager
    private lateinit var tabBar : TabLayout
    private lateinit var editBtn : FloatingActionButton
    private lateinit var trashBtn : FloatingActionButton


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
        viewPager = view.findViewById(R.id.view_pager)
        tabBar = view.findViewById(R.id.tab_bar)
        editBtn = view.findViewById(R.id.edit_btn)
        trashBtn = view.findViewById(R.id.trash_btn)
        var pagerAdapter = MenuDetailAdapter(
            requireActivity(),
            fragmentManager as FragmentManager,
            tabBar.tabCount
        )
        viewPager.adapter = pagerAdapter
        tabBar.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

        editBtn.setOnClickListener {
            viewModel.setToolbarTitle("Edit Menu")
            viewModel.setPage(Page.EDIT_MENU)
        }

        trashBtn.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("Delete Menu")
                .setMessage("Apakah anda yakin ingin menghapus menu ini ?")
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        if(viewModel.deleteMenu(viewModel.getChosenMenu().value?.idMenu)) {
                            val text = "Menu berhasil dihapus!"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(activity, text, duration)
                            toast.show()
                        }

                        viewModel.setPage(Page.LIST_MENU)
                    })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        viewModel.setToolbarTitle("Menu")
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
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

}