package com.example.randommenupicker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentMenuDetailDeskripsiBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class MenuDetailDeskripsiFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailDeskripsiBinding
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
        binding = FragmentMenuDetailDeskripsiBinding.inflate(inflater, container, false);
        viewModel.getChosenMenu().observe(this, {
            if(it != null){
                binding.tvNama.text = it.nama
                binding.tvDesc.text = it.deskripsi
                var tag = ""
                for (i in it.listTag) {
                    tag += i + "\n"
                }
                binding.tvTag.text = tag

                var bahan = ""
                for (i in it.listBahan) {
                    bahan += i + "\n"
                }
                binding.tvBahan.text = bahan
            }


        })
        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailDeskripsiFragment{
            var fragment = MenuDetailDeskripsiFragment()
            return fragment
        }
    }


}