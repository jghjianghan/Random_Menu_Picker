package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentMenuDetailEditBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import com.google.android.material.tabs.TabLayout

class MenuDetailEditFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailEditBinding
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var etNama : EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu_detail_edit, container, false)
        binding = FragmentMenuDetailEditBinding.bind(view)
//        binding = FragmentMenuDetailEditBinding.inflate(inflater, container, false);
        etNama = view.findViewById(R.id.et_nama)

        viewModel.getChosenMenu().observe(this, {
            println("CHOSEN MENU DI EDIT : " + it.nama)
            etNama.setText(it.nama)
            binding.etDesc.setText(it.deskripsi)
            var tag = ""
            for ((idx,i) in it.listTag.withIndex()) {
                if (idx > 0) tag += ";\n"
                tag += i
            }
            binding.etTag.setText(tag)

            var bahan = ""
            for ((idx,i) in it.listBahan.withIndex()) {
                if (idx > 0) bahan += ";\n"
                bahan += i
            }
            binding.etBahan.setText(bahan)

            var langkah = ""
            for ((idx,i) in it.listLangkah.withIndex()) {
                if (idx > 0) langkah += ";\n"
                langkah += i
            }
            binding.etLangkah.setText(langkah)

            var resto = ""
            for ((idx,i) in it.listResto.withIndex()) {
                if (idx > 0) resto += ";\n"
                resto += i
            }
            binding.etResto.setText(resto)
        })

        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailEditFragment{
            var fragment : MenuDetailEditFragment = MenuDetailEditFragment()
            return fragment
        }
    }


}