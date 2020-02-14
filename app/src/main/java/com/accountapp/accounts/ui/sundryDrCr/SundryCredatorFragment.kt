package com.accountapp.accounts.ui.sundryDrCr


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.databinding.FragmentSundryCredatorBinding
import com.accountapp.accounts.ui.home.HomeViewModel
import com.accountapp.accounts.ui.ladgerList.TrialBalanceListingActivites
import com.accountapp.accounts.utils.Utility
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SundryCredatorFragment : BaseFragment() {
    override fun getFragmentTag(): String {
        return TAG
    }

    lateinit var binding: FragmentSundryCredatorBinding
    val mContext by lazy { context }
    val mViewModel by lazy { ViewModelProviders.of(this).get(SundryViewModel::class.java) }
    private var fromDatePickerDialog: DatePickerDialog? = null
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US) //2018-10-18 MM-dd-yyyy
    private val dateFormatterForApi: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US) //2018-10-18 MM-dd-yyyy
    var apiDate:String = ""

    val TAG = "SundryCredatorFragment"

    companion object {
        val TAG = "SundryCredatorFragment"

        fun newInstance(args: Bundle?): SundryCredatorFragment {
            val fragment = SundryCredatorFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sundry_credator, container, false)

        binding.btnGetCom!!.setOnClickListener(View.OnClickListener {
            //            callReadCompanyApi()
            if (this!!.mContext?.let { it1 -> isInternetAvailable(binding.root, it1) }!!) {
                val intent = Intent(activity, SundryCrDrActivities::class.java)
                intent.putExtra("type", "CR")
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
