package com.example.randommenupicker.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.ItemListMenuBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_list_nav.view.*

class MenuAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: MainActivityViewModel,
    private var data : ArrayList<Menu>
): BaseAdapter() {

    private class ViewHolder (
        private var view: View,
        private var viewModel : MainActivityViewModel,
        private var idxItem : Int
    ) {
        private var binding = ItemListMenuBinding.bind(view)

        init {
            view.setOnClickListener {
                println("Idx : " + idxItem)
                viewModel.setChosenMenu(idxItem)
                viewModel.setPage(Page.MENU_DETAIL)
            }
        }

        public fun updateView(menu : String){
            binding.tvMenu.text = menu
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

            viewHolder = ViewHolder(view, viewModel, data[position].idMenu)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = (this.getItem(position) as Menu).nama +  " " +data[position].idMenu
        viewHolder.updateView(item)

        return view
    }

    fun updateList (list : ArrayList<Menu>) {
        this.data = ArrayList<Menu>(list)
        notifyDataSetChanged()
    }
}