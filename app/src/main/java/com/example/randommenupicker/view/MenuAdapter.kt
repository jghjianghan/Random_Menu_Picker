package com.example.randommenupicker.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.ItemListMenuBinding
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_list_nav.view.*

class MenuAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: MainActivityViewModel,
    private var data : Array<Menu>
): BaseAdapter() {

    private class ViewHolder (
        private var view: View
    ) : View.OnClickListener {
        private var binding = ItemListMenuBinding.bind(view)

        public fun updateView(menu : String){
            binding.tvMenu.text = menu
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
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
            view = inflater.inflate(R.layout.item_list_nav, parent, false)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = (this.getItem(position) as Menu).nama
        viewHolder.updateView(item)

        return view
    }
}