package com.example.randommenupicker.view

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.ItemListMenuBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.model.MenuAttribute
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_list_nav.view.*
import java.util.*
import java.util.function.Predicate
import kotlin.Comparator
import kotlin.collections.ArrayList

class MenuAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: MainActivityViewModel,
    private var data : List<Menu>
): BaseAdapter() {

    private class ViewHolder (
        private var view: View,
        private var viewModel : MainActivityViewModel,
        private var menu : Menu
    ) {
        private var binding = ItemListMenuBinding.bind(view)

        init {
            view.setOnClickListener {
                println("Idx : " + menu.idMenu)
                viewModel.setChosenMenu(menu.idMenu)
                viewModel.setPage(Page.MENU_DETAIL)
            }
        }

        fun updateView(menuData : Menu){
            binding.tvMenu.text = menuData.nama
            menu = menuData
        }
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder : ViewHolder
        lateinit var view : View
        if(convertView == null) {
            view = inflater.inflate(R.layout.item_list_menu, parent, false)

            viewHolder = ViewHolder(view, viewModel, data[position])
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = (this.getItem(position) as Menu)
        viewHolder.updateView(item)

        return view
    }

    fun updateList (list : ArrayList<Menu>) {
        this.data = ArrayList(list)
        notifyDataSetChanged()
        println("update: adapter size ${count}")
    }

    fun sortData(comparator: Comparator<Menu>){
        Collections.sort(data, comparator)
        notifyDataSetChanged()
        println("sort: adapter size ${count}")
    }
    fun filterData(keyword:String, category: MenuAttribute){
        data = when (category){
            MenuAttribute.NAMA -> data.filter {it.namaContains(keyword)}
            MenuAttribute.DESKRIPSI -> data.filter {it.deskripsiContains(keyword)}
            MenuAttribute.BAHAN -> data.filter {it.bahanContains(keyword)}
            MenuAttribute.TAG -> data.filter {it.tagContains(keyword)}
            MenuAttribute.RESTO -> data.filter {it.restoContains(keyword)}
        }
        notifyDataSetChanged()
    }
}