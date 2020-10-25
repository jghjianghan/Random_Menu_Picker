package com.example.randommenupicker.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.FragmentMenuDetailEditBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel


class MenuDetailEditFragment : Fragment() {
    private lateinit var binding : FragmentMenuDetailEditBinding
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var etNama : EditText
    private var isAdd = false;
    private var menuId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu_detail_edit, container, false)
        binding = FragmentMenuDetailEditBinding.bind(view)
//        binding = FragmentMenuDetailEditBinding.inflate(inflater, container, false);
        etNama = view.findViewById(R.id.et_nama)

        viewModel.getChosenMenu().observe(this, {
            var it2 = it;
            if (it2 == null) {
                it2 = Menu("", "", "", "", "", "")
                isAdd = true
            } else {
                isAdd = false
                menuId = it.idMenu
            }
            println("CHOSEN MENU DI EDIT : " + it2.nama)
            etNama.setText(it2.nama)
            binding.etDesc.setText(it2.deskripsi)
            var tag = ""
            for ((idx, i) in it2.listTag.withIndex()) {
                if (idx > 0) tag += ";\n"
                tag += i
            }
            binding.etTag.setText(tag)

            var bahan = ""
            for ((idx, i) in it2.listBahan.withIndex()) {
                if (idx > 0) bahan += ";\n"
                bahan += i
            }
            binding.etBahan.setText(bahan)

            var langkah = ""
            for ((idx, i) in it2.listLangkah.withIndex()) {
                if (idx > 0) langkah += ";\n"
                langkah += i
            }
            binding.etLangkah.setText(langkah)

            var resto = ""
            for ((idx, i) in it2.listResto.withIndex()) {
                if (idx > 0) resto += ";\n"
                resto += i
            }
            binding.etResto.setText(resto)
        })

        binding.btnSave.setOnClickListener {
            if(isAdd) {
                if (viewModel.addMenu(
                        binding.etNama.text.toString(),
                        binding.etDesc.text.toString(),
                        binding.etTag.text.toString(),
                        binding.etBahan.text.toString(),
                        binding.etLangkah.text.toString(),
                        binding.etResto.text.toString()
                    )
                ) {
                    val text = "Menu berhasil ditambah!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(activity, text, duration)
                    toast.show()
                }

            }else {

                if (viewModel.editMenu(
                        menuId,
                        binding.etNama.text.toString(),
                        binding.etDesc.text.toString(),
                        binding.etTag.text.toString(),
                        binding.etBahan.text.toString(),
                        binding.etLangkah.text.toString(),
                        binding.etResto.text.toString()
                    )
                ) {
                    val text = "Menu berhasil diedit!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(activity, text, duration)
                    toast.show()
                }
            }
            viewModel.setPage(Page.POP_PAGE)
        }

        binding.btnDiscard.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("Discard")
                .setMessage("Apakah anda yakin ingin membatalkan?")
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        viewModel.setPage(Page.POP_PAGE)
                    })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        return binding.root
    }


    companion object {
        fun newInstance() : MenuDetailEditFragment{
            var fragment : MenuDetailEditFragment = MenuDetailEditFragment()
            return fragment
        }
    }


}