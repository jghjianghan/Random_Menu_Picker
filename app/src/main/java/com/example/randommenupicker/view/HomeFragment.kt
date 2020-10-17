package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentHomeBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var activity: MainActivity
    private lateinit var viewModel: MainActivityViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            activity = context
            viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        } else {
            throw ClassCastException("$context tidak sesuai")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.getChosenMenu().observe(this, {
            binding.tvResult.text = it.nama
        })
        binding.btnCari.setOnClickListener {
            viewModel.getRandomMenu()
        }
        return binding.root
    }


    companion object {
        fun newInstance() : HomeFragment{
            var fragment : HomeFragment = HomeFragment()
            return fragment
        }
    }


}