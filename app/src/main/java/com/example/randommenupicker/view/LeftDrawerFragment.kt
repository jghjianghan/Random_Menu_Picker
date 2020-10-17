package com.example.randommenupicker.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentLeftDrawerBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class LeftDrawerFragment : Fragment() {
    private lateinit var binding : FragmentLeftDrawerBinding
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
        binding = FragmentLeftDrawerBinding.inflate(inflater, container, false);


        return binding.root
    }

    companion object {
        fun newInstance() : LeftDrawerFragment{
            val fragment = LeftDrawerFragment()

            return fragment
        }
    }

}