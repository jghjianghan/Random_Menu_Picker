package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentMenuDetailLangkahBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class MenuDetailLangkahFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailLangkahBinding
    private lateinit var viewModel : MainActivityViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("ON CREATE VIEW MENU DETAIL LANGKAH")
        binding = FragmentMenuDetailLangkahBinding.inflate(inflater, container, false);
        viewModel.getChosenMenu().observe(this,{

            if(it != null) {
                var langkah  = ""
                for((idx,value) in it.listLangkah.withIndex()) {
                    langkah += (idx+1).toString() + ". " + value + "\n"
                }

                binding.tvLangkah.text = langkah
            }
        })
        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailLangkahFragment{
            println("LANGKAH NEW INSTANCE")
            var fragment = MenuDetailLangkahFragment()
            return fragment
        }
    }

    override fun onDestroy() {
        println("MENU DETAIL LANGKAH ON DESTROY")
        super.onDestroy()
    }


}