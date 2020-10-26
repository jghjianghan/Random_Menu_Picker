package com.example.randommenupicker.view
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.randommenupicker.R
import com.example.randommenupicker.model.History
import com.example.randommenupicker.model.Menu
import com.example.randommenupicker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_list_history.view.*

class SearchHistoryAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: MainActivityViewModel,
    private var data : List<History>,

): BaseAdapter() {
    private lateinit var dataDisplay : List<History>
    init {
        dataDisplay = ArrayList<History>(data)
    }

    override fun getCount(): Int {
        return dataDisplay.size
    }

    override fun getItem(position: Int): Any {
        return dataDisplay[getCount() - 1 - position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.item_list_history, parent, false)
        val item = this.getItem(position) as History
        view.tag = item
        view.tv_history.text = item.keyword

        view.tv_history.setOnClickListener {
            viewModel.setSearchBarKeyword(item.keyword)
            notifyDataSetChanged()
        }

        view.btn_remove.setOnClickListener {
            viewModel.deleteHistory(item.idHistory)
            notifyDataSetChanged()
        }

        return view
    }

    fun filter(query : String) {
        dataDisplay = data.filter {
            it.keyword.toLowerCase().contains(query.toLowerCase())
        }
        notifyDataSetChanged()
    }
    fun updateList (list : ArrayList<History>) {
        this.data = ArrayList(list)
//        notifyDataSetChanged()
        filter(viewModel.getSearchBarKeyword().value as String)
    }
}