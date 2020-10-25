package com.example.randommenupicker.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.databinding.FragmentSettingBinding
import com.example.randommenupicker.viewmodel.MainActivityViewModel

class SettingFragment : Fragment(), TextWatcher, CompoundButton.OnCheckedChangeListener {
    private lateinit var binding : FragmentSettingBinding
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
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        viewModel.getRandomLimit().observe(this, {
            if(binding.etResult.text.toString() != it.toString()){
                binding.etResult.setText(it.toString())
            }
        })

        binding.btnMin.setOnClickListener {
            var count = viewModel.getRandomLimit().value as Int
            if(count > 0 ) {
                viewModel.setRandomLimit(count - 1)
            }
        }

        binding.btnPlus.setOnClickListener {
            var count = viewModel.getRandomLimit().value as Int
            viewModel.setRandomLimit(count + 1)
        }

        binding.btnClear.setOnClickListener {

            AlertDialog.Builder(activity)
                .setTitle("Clear search history ?")
                .setMessage("Apakah anda yakin ingin menghapus seluruh search history?")
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        viewModel.clearSearchHistory()
                    })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        binding.etResult.addTextChangedListener(this)

        binding.switchSearchHistory.isChecked = viewModel.getSearchHistoryStatus().value as Boolean
        viewModel.getSearchHistoryStatus().observe(this, {
            if(binding.switchSearchHistory.isChecked != it){
                binding.switchSearchHistory.isChecked = it
            }
        })

        binding.switchSearchHistory.setOnCheckedChangeListener(this)

        return binding.root
    }


    companion object {
        fun newInstance() : SettingFragment{
            var fragment = SettingFragment()
            return fragment
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        var count = Integer.parseInt(binding.etResult.text.toString())
        if(count < 0) count = 0
        viewModel.setRandomLimit(count)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        viewModel.switchSearchHistoryStatus()
    }
}