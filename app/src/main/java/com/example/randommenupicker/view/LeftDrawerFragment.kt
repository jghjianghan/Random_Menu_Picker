package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentLeftDrawerBinding
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class LeftDrawerFragment : Fragment() {
    private lateinit var binding : FragmentLeftDrawerBinding
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
        binding = FragmentLeftDrawerBinding.inflate(inflater, container, false);
        binding.listNav.adapter = NavAdapter(inflater, viewModel, Page.values())

        return binding.root
    }

    companion object {
        fun newInstance() : LeftDrawerFragment{
            val fragment = LeftDrawerFragment()

            return fragment
        }
    }

}