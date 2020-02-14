package com.accountapp.accounts.ui.sundryDrCr


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.databinding.FragmentSundryDebatorsBinding
import com.accountapp.accounts.ui.home.HomeViewModel
import com.accountapp.accounts.ui.ladgerList.TrialBalanceListingActivites
import com.accountapp.accounts.utils.Utility
import java.text.SimpleDateFormat
import java.util.*


class SundryDebatorsFragment : BaseFragment() {
    override fun getFragmentTag(): String {

        return TAG
    }

    lateinit var binding: FragmentSundryDebatorsBinding
    val mContext by lazy { context }
    val mViewModel by lazy { ViewModelProviders.of(this).get(SundryViewModel::class.java) }
    private var fromDatePickerDialog: DatePickerDialog? = null
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
    private val dateFormatterForApi: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US) //2018-10-18 MM-dd-yyyy
    var apiDate:String = ""
    val TAG = "SundryDebatorsFragment"

    companion object {
        val TAG = "SundryDebatorsFragment"

        fun newInstance(args: Bundle?): SundryDebatorsFragment {
            val fragment = SundryDebatorsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sundry_debators, container, false)

        binding.btnGetCom!!.setOnClickListener(View.OnClickListener {
            //            callReadCompanyApi()
            if (this!!.mContext?.let { it1 -> isInternetAvailable(binding.root, it1) }!!) {
                val intent = Intent(activity, SundryCrDrActivities::class.java)
                intent.putExtra("type", "DR")
//                intent.putExtra("fromdate", binding.fromDate.text.toString())
                intent.putExtra("todate", binding.toDate.text.toString())
                intent.putExtra("todateForApi", apiDate)
                Utility.startActivityWithLeftToRightAnimation(activity, intent)
            }

        })

        binding.toDate.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            fromDatePickerDialog = mContext?.let { it1 ->
                DatePickerDialog(
                    it1,
                    R.style.datepicker,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val newDate = Calendar.getInstance()
                        newDate.set(year, monthOfYear, dayOfMonth)
                        binding.toDate.setText(dateFormatter.format(newDate.getTime()))
                        apiDate=dateFormatterForApi.format(newDate.getTime())
                    },
                    newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(
                        Calendar.DAY_OF_MONTH
                    )
                )
            }
//            fromDatePickerDialog!!.datePicker.minDate = newCalendar.timeInMillis
            fromDatePickerDialog!!.show()
        }

        return binding.root
    }


}
