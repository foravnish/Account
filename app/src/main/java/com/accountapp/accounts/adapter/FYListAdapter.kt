package com.accountapp.accounts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.R
import com.accountapp.accounts.model.response.CompanyListingResponse
import com.accountapp.accounts.model.response.FYModel
import kotlinx.android.synthetic.main.block_list_item_raw.view.*

class FYListAdapter(val mList: ArrayList<FYModel>, var itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<FYListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.block_list_item_raw, parent, false)
        return MyHolder(v)
    }

    fun addList(mUpdateList: ArrayList<FYModel>) {
        mList.clear()
        mList.addAll(mUpdateList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(mList: FYModel) {
            itemView.lytClickCity.setOnClickListener { itemClick(adapterPosition) }

            itemView.txtBlockName.text = mList.fy
//            itemView.txtStateCountry.text= mList.adddress
        }
    }


}