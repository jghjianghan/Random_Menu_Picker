package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentCariBinding
import com.example.randommenupicker.databinding.FragmentMenuBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class CariFragment : Fragment() {
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
        
        return binding.root
    }


    companion object {
        fun newInstance() : CariFragment{
            var fragment : CariFragment = CariFragment()
            return fragment
        }
    }


}