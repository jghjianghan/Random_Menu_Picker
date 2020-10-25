package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentHomeBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.model.Page
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
                binding.tvRandomLabel.visibility = View.GONE
                binding.tvClickHint.visibility = View.GONE
                binding.tvResult.text = ""
                Toast.makeText(requireActivity(),getString(R.string.randomEmptyMenu),Toast.LENGTH_LONG).show();
            } else {
                binding.tvRandomLabel.visibility = View.VISIBLE
                binding.tvClickHint.visibility = View.VISIBLE
                val spannableString = SpannableString(it.nama)
                spannableString.setSpan(UnderlineSpan(), 0, it.nama.length, 0)
                binding.tvResult.text = spannableString
            }
        })
        binding.tvResult.setOnClickListener{
            viewModel.setChosenMenu((viewModel.getRandomChosenMenu().value as Menu).idMenu)
            viewModel.setPage(Page.MENU_DETAIL)
        }

        binding.btnCari.setOnClickListener {
            viewModel.getRandomMenu()
        }
        viewModel.setToolbarTitle("Random Menu Picker")
        return binding.root
    }


    companion object {
        fun newInstance() : HomeFragment{
            var fragment : HomeFragment = HomeFragment()
            return fragment
        }
    }


}