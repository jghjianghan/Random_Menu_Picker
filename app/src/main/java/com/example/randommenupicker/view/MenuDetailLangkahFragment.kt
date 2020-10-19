package com.example.randommenupicker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randommenupicker.databinding.FragmentMenuDetailLangkahBinding

class MenuDetailLangkahFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailLangkahBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuDetailLangkahBinding.inflate(inflater, container, false);
        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailLangkahFragment{
            var fragment : MenuDetailLangkahFragment = MenuDetailLangkahFragment()
            return fragment
        }
    }


}