package com.example.randommenupicker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randommenupicker.databinding.FragmentMenuDetailDeskripsiBinding

class MenuDetailDeskripsiFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailDeskripsiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuDetailDeskripsiBinding.inflate(inflater, container, false);
        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailDeskripsiFragment{
            var fragment : MenuDetailDeskripsiFragment = MenuDetailDeskripsiFragment()
            return fragment
        }
    }


}