package com.accountapp.accounts.ui.ladgerList

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityDateSelectionBinding
import com.accountapp.accounts.ui.home.HomeActivity
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper
import java.text.SimpleDateFormat
import java.util.*

class DateSelectionActivity : BaseActivity() {

    lateinit var binding: ActivityDateSelectionBinding
    val mContext by lazy { this@DateSelectionActivity }

    private var fromDatePickerDialog: DatePickerDialog? = null
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US) //2018-10-18 MM-dd-yyyy

    lateinit var comId:String
    lateinit var comName:String

    override fun initUI() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_selection)

        comId=intent.getStringExtra("ACC_ID")
        comName=intent.getStringExtra("COM_NAME")


        binding.fromDate.setOnClickListener {

            val newCalendar = Calendar.getInstance()
            fromDatePickerDialog = DatePickerDialog(this, R.style.datepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)
                    binding.fromDate.setText(dateFormatter.format(newDate.getTime()))
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
//            fromDatePickerDialog!!.datePicker.minDate = newCalendar.timeInMillis
            fromDatePickerDialog!!.show()
        }

        binding.toDate.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            fromDatePickerDialog = DatePickerDialog(this, R.style.datepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)
                    binding.toDate.setText(dateFormatter.format(newDate.getTime()))
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
//            fromDatePickerDialog!!.datePicker.minDate = newCalendar.timeInMillis
            fromDatePickerDialog!!.show()
        }


        binding.submit.setOnClickListener {

            if (isValidate()) {
                val intent = Intent(this@DateSelectionActivity, LadgerListingActivity::class.java)
                intent.putExtra("ACC_ID",comId)
                intent.putExtra("COM_NAME",comName)
                intent.putExtra("fromdate",binding.fromDate.text.toString())
                intent.putExtra("todate",binding.toDate.text.toString())
                Utility.startActivityWithLeftToRightAnimation(this@DateSelectionActivity,intent)
            }


//            val intent = Intent(this@DateSelectionActivity, HomeActivity::class.java)
//            intent.putExtra("fromdate",binding.fromDate.text.toString())
//            intent.putExtra("todate",binding.toDate.text.toString())
//            Utility.startActivityWithLeftToRightAnimation(this@DateSelectionActivity,intent)






        }


    }
    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.fromDate,
                getString(R.string.err_from_date),
                binding.root
            )
        ) {
            if (ValidationHelper.isDataFilled(
                    binding.toDate,
                    getString(R.string.err_end_date),
                    binding.root
                )
            ) {
                return true
            }
        }

        return false
    }


}
