package com.accountapp.accounts.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.R
import com.accountapp.accounts.databinding.RowItemLedgerBinding
import com.accountapp.accounts.model.response.DataItemLadger
import kotlinx.android.synthetic.main.row_item_ledger.view.*

class LedgerCompanyAdapter() : RecyclerView.Adapter<LedgerCompanyAdapter.MyViewHolder>() {
    lateinit var binding: RowItemLedgerBinding
    var list: MutableList<DataItemLadger>? = null

    var drTotal=0.0
    var crTotal=0.0
    var balTotal=0.0
    lateinit var callback: TotalCallback
    class MyViewHolder(binding: RowItemLedgerBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(list: MutableList<DataItemLadger>) {
        if (this.list!=null){
            this.list!!.clear()
        }
        this.list = list
        notifyDataSetChanged()

    }


    fun setViewCallback(callback:TotalCallback){
        this.callback=callback
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LedgerCompanyAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_ledger, p0, false)
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
                //                callback.onSearchClick(list!!.get(position).ACCODE)
            }



            var credit=list!!.get(position).CREDIT.toString().toDouble()
            var debit=list!!.get(position).DEBIT.toString().toDouble()
            var balance=list!!.get(position).BALANCE.toString().toInt()
            crTotal=crTotal+credit
            drTotal=drTotal+debit
            balTotal=balTotal+balance
            callback!!.onTotalCallback(crTotal,drTotal,balTotal)

            holder.itemView.txtTitle.setText(""+list!!.get(position).ACNAME)
            holder.itemView.txtTitle.setText("" + list!!.get(position).NARR)
            holder.itemView.txtDate.setText("" + list!!.get(position).DATE)
            holder.itemView.txtAmount.setText("" + list!!.get(position).DEBIT)
            holder.itemView.txtCredit.setText("" + list!!.get(position).CREDIT)
            holder.itemView.txtBalance.setText("" + list!!.get(position).BALANCE)

//            for (i in 0 until list!!.size) {
                if (position%2==1){
                    holder.itemView.linearBackColor.setBackgroundColor(Color.parseColor("#BBCFD1"))
                }else{
                    holder.itemView.linearBackColor.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
//            }


        }

    }


    interface TotalCallback {
        fun onTotalCallback(crTotal: Double,drTotal: Double,balTotal: Double)
    }

}