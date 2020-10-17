package com.example.randommenupicker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randommenupicker.databinding.FragmentMenuDetailEditBinding

class MenuDetailEditFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailEditBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuDetailEditBinding.inflate(inflater, container, false);
        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailEditFragment{
            var fragment : MenuDetailEditFragment = MenuDetailEditFragment()
            return fragment
        }
    }


}