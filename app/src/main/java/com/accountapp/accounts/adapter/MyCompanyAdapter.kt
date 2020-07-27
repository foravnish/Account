package com.accountapp.accounts.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.R
import com.accountapp.accounts.databinding.RowItemMyCompanyBinding
import com.accountapp.accounts.model.response.DataItemComListing
import com.accountapp.accounts.model.response.DataItemLadger
import com.accountapp.accounts.ui.home.HomeActivity
import kotlinx.android.synthetic.main.row_item_ledger.view.*
import kotlinx.android.synthetic.main.row_item_my_company.view.*

class MyCompanyAdapter() : RecyclerView.Adapter<MyCompanyAdapter.MyViewHolder>() {
    lateinit var binding: RowItemMyCompanyBinding
    var list: MutableList<DataItemComListing>? = null
    var lastCheckedRadioGroup: RadioGroup? = null

    lateinit var callback: SelectionCallback
    class MyViewHolder(binding: RowItemMyCompanyBinding) : RecyclerView.ViewHolder(binding.root)
    lateinit var radiobtn: RadioButton
    var liLayout: LinearLayout? = null
    fun setData(list: MutableList<DataItemComListing>) {
        if (this.list!=null){
            this.list!!.clear()
        }
        this.list = list
        notifyDataSetChanged()

    }


    fun setViewCallback(callback:SelectionCallback){
        this.callback=callback
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyCompanyAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_my_company, p0, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (list != null && list!!.size > 0)
            return list!!.size
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (holder != null) {
            holder.itemView.tv_gst.setText("" + list!!.get(position).gst)

            val rb = RadioButton(holder.itemView.context)

            rb.textSize = 16f
            rb.setText("   " + list!!.get(position).company)
            if (position == 0) {
                radiobtn=rb
                rb.isChecked = true

                holder.itemView.btn_edit2.visibility = View.VISIBLE
                liLayout = holder.itemView.btn_edit2

            } else {
                rb.isChecked = false
            }
            holder.itemView.cb_name_parent_grup.addView(rb)

            holder.itemView.cb_name_parent_grup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioGroup: RadioGroup?, checkedId: Int) {
                    if (lastCheckedRadioGroup != null
                        && lastCheckedRadioGroup!!.getCheckedRadioButtonId() !== radioGroup!!.getCheckedRadioButtonId()
                        && lastCheckedRadioGroup!!.getCheckedRadioButtonId() !== -1)
                    {
                        lastCheckedRadioGroup!!.clearCheck()

                    }else{
                        radiobtn.isChecked=false
                    }

                    if (liLayout != null) {
                        liLayout!!.visibility = View.GONE
                        holder.itemView.btn_edit2.visibility = View.VISIBLE
                        liLayout = holder.itemView.btn_edit2

                    } else {
                        holder.itemView.btn_edit2.visibility = View.VISIBLE
                        liLayout = holder.itemView.btn_edit2
                    }
                    lastCheckedRadioGroup = radioGroup!!
                    }

            })


            holder.itemView.btn_delivery.setOnClickListener(View.OnClickListener {

                callback!!.onSelectCompany(list!!.get(position).company,list!!.get(position).gst)

            })


        }
    }


    interface SelectionCallback {
        fun onSelectCompany(company: String,gst: String)

    }

}