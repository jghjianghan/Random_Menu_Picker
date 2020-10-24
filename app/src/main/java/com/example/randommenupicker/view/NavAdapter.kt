package com.example.randommenupicker.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.randommenupicker.R
import com.example.randommenupicker.databinding.ItemListNavBinding
import com.example.randommenupicker.model.Page
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_list_nav.view.*

class NavAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: MainActivityViewModel,
    private var data : Array<Page>
): BaseAdapter() {

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
        val view = inflater.inflate(R.layout.item_list_nav, parent, false)
        val item = this.getItem(position) as Page
        view.tag = item
        view.tv_nav.text = item.text
        view.setOnClickListener{
            println(view.tag)
            if(view.tag as Page == Page.LIST_MENU) viewModel.loadAllMenu()
            viewModel.setPage(view.tag as Page)
        }

        return view
    }
}