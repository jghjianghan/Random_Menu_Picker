package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentMenuDetailDeskripsiBinding
import com.example.randommenupicker.databinding.FragmentMenuDetailRestoBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class MenuDetailRestoFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailRestoBinding
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
        println("ON CREATE VIEW MENU DETAIL RESTO")
        binding = FragmentMenuDetailRestoBinding.inflate(inflater, container, false);
        viewModel.getChosenMenu().observe(this,{
            if(it != null) {
                var resto  = ""
                for((idx,value) in it.listResto.withIndex()) {
                    resto += (idx+1).toString() + ". " + value + "\n"
                }

                binding.tvResto.text = resto
            }
        })

        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailRestoFragment{
            println("RESTO NEW INSTANCE")
            var fragment = MenuDetailRestoFragment()
            return fragment
        }
    }

    override fun onDestroy() {
        println("MENU DETAIL RESTO ON DESTROY")
        super.onDestroy()
    }


}