package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentHomeBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.getRandomChosenMenu().observe(requireActivity(), {
            if (it == null){
                binding.tvResult.text = ""
                Toast.makeText(requireActivity(),getString(R.string.randomEmptyMenu),Toast.LENGTH_LONG).show();
            } else {
                binding.tvResult.text = it.nama
            }
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