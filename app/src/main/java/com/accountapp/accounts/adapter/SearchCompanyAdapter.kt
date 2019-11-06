package com.accountapp.accounts.adapter

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.R
import com.accountapp.accounts.databinding.RowItemSearchBinding
import com.accountapp.accounts.model.response.DataItem
import kotlinx.android.synthetic.main.row_item_search.view.*

class SearchCompanyAdapter() : RecyclerView.Adapter<SearchCompanyAdapter.MyViewHolder>() {
    lateinit var binding: RowItemSearchBinding
    lateinit var callback: SearchClick
    var list: MutableList<DataItem>? = null

    class MyViewHolder(binding: RowItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(list: MutableList<DataItem>) {
        if (this.list!=null){
            this.list!!.clear()
        }
        this.list = list
        notifyDataSetChanged()

    }


    fun setViewCallback(callback:SearchClick){
        this.callback=callback
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchCompanyAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_search, p0, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (list != null && list!!.size > 0)
            return list!!.size
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (holder != null) {
            holder.itemView.setOnClickListener {
                callback.onSearchClick(list!!.get(position).ACCODE,list!!.get(position).ACNAME)
            }

            holder.itemView.title.setText(""+list!!.get(position).ACNAME)
        }

    }


    interface SearchClick{
        fun onSearchClick(pId: String,com_name: String)
    }

}