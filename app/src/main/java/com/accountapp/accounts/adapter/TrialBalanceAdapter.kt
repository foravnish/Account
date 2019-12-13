package com.accountapp.accounts.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.R
import com.accountapp.accounts.databinding.RowItemTrialBalanceBinding
import com.accountapp.accounts.model.response.DataItemTB
import kotlinx.android.synthetic.main.row_item_ledger.view.*

class TrialBalanceAdapter() : RecyclerView.Adapter<TrialBalanceAdapter.MyViewHolder>() {
    lateinit var binding: RowItemTrialBalanceBinding
    var list: MutableList<DataItemTB>? = null

    var drTotal = 0.0
    var crTotal = 0.0
    var balTotal = 0.0
    var credit = 0.0
    var debit = 0.0

    lateinit var callback: TotalCallback

    class MyViewHolder(binding: RowItemTrialBalanceBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(list: MutableList<DataItemTB>) {
        if (this.list != null) {
            this.list!!.clear()
        }
        this.list = list
        notifyDataSetChanged()

    }


    fun setViewCallback(callback: TotalCallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TrialBalanceAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_item_trial_balance, p0, false)
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



            if (list!!.get(position).cr == "") {
                credit = 0.0
            } else {
                credit = list!!.get(position).cr.toString().toDouble()
            }
            if (list!!.get(position).dr == "") {
                debit = 0.0
            } else {
                debit = list!!.get(position).dr.toString().toDouble()
            }

            // var balance=list!!.get(position).BALANCE.toString().toInt()
            crTotal = crTotal + credit
            drTotal = drTotal + debit
            //  balTotal=balTotal+balance
           // callback!!.onTotalCallback(crTotal, drTotal, balTotal)

            //  holder.itemView.txtTitle.setText(""+list!!.get(position).accode)
            holder.itemView.txtTitle.setText("" + list!!.get(position).acname)
            holder.itemView.txtDate.setText("" + list!!.get(position).accode)
            holder.itemView.txtAmount.setText("" + list!!.get(position).dr)
            holder.itemView.txtCredit.setText("" + list!!.get(position).cr)
            //  holder.itemView.txtBalance.setText("" + list!!.get(position).BALANCE)

//            for (i in 0 until list!!.size) {
            if (position % 2 == 1) {
                holder.itemView.linearBackColor.setBackgroundColor(Color.parseColor("#BBCFD1"))
            } else {
                holder.itemView.linearBackColor.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
//            }


        }

    }


    interface TotalCallback {
        fun onTotalCallback(crTotal: Double, drTotal: Double, balTotal: Double)
    }

}