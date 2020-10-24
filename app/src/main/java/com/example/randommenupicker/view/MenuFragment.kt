package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentMenuBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.model.SortOption
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*

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
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        var adapter = MenuAdapter(inflater, viewModel, ArrayList<Menu>())
        binding.listMenu.adapter = adapter
        viewModel.getFilteredMenuList().observe(requireActivity(),{
            adapter.updateList(it)
        })

        binding.fabBtn.setOnClickListener {
            viewModel.setChosenMenu(-1)
            viewModel.setToolbarTitle("Add Menu")
            viewModel.setPage(Page.EDIT_MENU)
        }
        val spinnerAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.sort_option, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortSpinner.adapter = spinnerAdapter

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("sortopt $position")
                viewModel.sortMenu(when(position){
                    0->SortOption.NAME_ASC
                    1->SortOption.NAME_DESC
                    2->SortOption.TIME_ADDED_ASC
                    else ->SortOption.TIME_ADDED_DESC
                })
            }
        }

        viewModel.getMenuTitle().observe(this, {
            setTitle(it)
        })

        return binding.root
    }

    fun setTitle(title: String){
        if (title.isEmpty()){
            binding.tvTitle.visibility = View.GONE
        } else {
            binding.tvTitle.text = title
            binding.tvTitle.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() : MenuFragment{
            var fragment = MenuFragment()

            return fragment
        }
    }



}