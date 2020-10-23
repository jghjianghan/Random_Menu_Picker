package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentMenuBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class MenuFragment : Fragment() {
    private lateinit var binding : FragmentMenuBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var fragmentDetail: MenuDetailFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        viewModel.loadMenu()

        var adapter = MenuAdapter(inflater, viewModel, ArrayList<Menu>())
        binding.listMenu.adapter = adapter
        viewModel.getFilteredMenuList().observe(requireActivity(),{
            adapter.updateList(it)
        })
        viewModel.loadAllMenu()

        binding.fabBtn.setOnClickListener {
            viewModel.addMenu("","","","","","")
            viewModel.setChosenMenu(viewModel.getMenuSize() - 1)
            viewModel.setPage(Page.EDIT_MENU)
        }
        return binding.root
    }


    companion object {
        fun newInstance() : MenuFragment{
            var fragment = MenuFragment()
            return fragment
        }
    }



}