package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentCariBinding
import com.example.randommenupicker.model.History
import com.example.randommenupicker.model.MenuAttribute
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel


class CariFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding : FragmentCariBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCariBinding.inflate(inflater, container, false);
        viewModel.setSearchBarKeyword("")
        var adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.spinner_kategori,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = adapter
        binding.spinnerKategori.onItemSelectedListener = this

        binding.searchBar.setQuery(viewModel.getSearchBarKeyword().value, false)

        viewModel.getSearchBarKeyword().observe(this, {
            binding.searchBar.setQuery(it, false)
        })


        var atr : MenuAttribute
        when(binding.spinnerKategori.selectedItemPosition) {
            0 -> atr = MenuAttribute.NAMA
            1 -> atr = MenuAttribute.DESKRIPSI
            2 -> atr = MenuAttribute.TAG
            3 -> atr = MenuAttribute.BAHAN
            else -> atr = MenuAttribute.RESTO
        }
        var filteredHistoryList = viewModel.getFilteredHistoryList(atr)
        var historyAdapter = SearchHistoryAdapter(inflater, viewModel, filteredHistoryList)
        binding.listviewHistory.adapter = historyAdapter

        viewModel.getSearchHistory().observe(this, {
            historyAdapter.updateList(it)

        })

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()
                val imm: InputMethodManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)

                var atr : MenuAttribute
                when(binding.spinnerKategori.selectedItemPosition) {
                    0 -> atr = MenuAttribute.NAMA
                    1 -> atr = MenuAttribute.DESKRIPSI
                    2 -> atr = MenuAttribute.TAG
                    3 -> atr = MenuAttribute.BAHAN
                    else -> atr = MenuAttribute.RESTO
                }
                viewModel.addToHistory(binding.searchBar.query.toString(), atr)
                viewModel.setSearchBarKeyword(binding.searchBar.query.toString())
                viewModel.setLiveCategory(atr)

                binding.searchBar.setQuery("", false);
                viewModel.setPage(Page.LIST_MENU_DARI_CARI)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(filteredHistoryList.contains(newText)) {
                    if(newText != null) {
                        historyAdapter.filter(newText)
                        viewModel.setSearchBarKeyword(newText)
                    }
                }
                return false
            }
        })

        binding.searchBar.setOnQueryTextFocusChangeListener {
                v, hasFocus ->
            if (hasFocus){
                binding.listviewHistory.visibility = View.VISIBLE
            } else {
                binding.listviewHistory.visibility = View.GONE
            }
        }

        binding.searchBar.setOnClickListener {
            binding.searchBar.setIconified(false);
        }

        binding.btnCari.setOnClickListener {
            val imm: InputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)

            var atr : MenuAttribute
            when(binding.spinnerKategori.selectedItemPosition) {
                0 -> atr = MenuAttribute.NAMA
                1 -> atr = MenuAttribute.DESKRIPSI
                2 -> atr = MenuAttribute.TAG
                3 -> atr = MenuAttribute.BAHAN
                else -> atr = MenuAttribute.RESTO
            }
            viewModel.addToHistory(binding.searchBar.query.toString(), atr)
            viewModel.setSearchBarKeyword(binding.searchBar.query.toString())
            viewModel.setLiveCategory(atr)
            binding.searchBar.setQuery("", false)
            viewModel.setPage(Page.LIST_MENU_DARI_CARI)
        }
        viewModel.setToolbarTitle("Cari")
        return binding.root
    }


    companion object {
        fun newInstance() : CariFragment{
            var fragment = CariFragment()
            return fragment
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.setLiveCategory(
            when(binding.spinnerKategori.selectedItemPosition) {
            0 -> MenuAttribute.NAMA
            1 -> MenuAttribute.DESKRIPSI
            2 -> MenuAttribute.TAG
            3 -> MenuAttribute.BAHAN
            else -> MenuAttribute.RESTO
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}