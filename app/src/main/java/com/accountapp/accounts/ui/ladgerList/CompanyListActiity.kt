package com.accountapp.accounts.ui.ladgerList

import android.app.DatePickerDialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.R
import com.accountapp.accounts.adapter.SearchCompanyAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityLadgerListActiityBinding
import com.accountapp.accounts.model.response.DataItem
import com.accountapp.accounts.model.response.SearchCompanyList
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import org.apache.commons.lang3.text.WordUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompanyListActiity : BaseActivity(), SearchCompanyAdapter.SearchClick {


    lateinit var binding: ActivityLadgerListActiityBinding
    val mContext by lazy { this@CompanyListActiity }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(ListViewModel::class.java) }

    lateinit var mSearchCompany: SearchCompanyAdapter
    var mResultSearchData: MutableList<DataItem>? = null

    var comId: String = ""
    var comName: String = ""

    private var fromDatePickerDialog: DatePickerDialog? = null
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US) //2018-10-18 MM-dd-yyyy

    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ladger_list_actiity)
//        setToolbarWithBackIcon(
//            binding.includedToolbar.findViewById(R.id.toolbar),
//            getString(R.string.com_list) )

        binding.closePress.setOnClickListener {
            binding.searchEdit.setText("")
            callSearchProductApi("")
            binding.dateLayout.setVisibility(View.GONE)
            binding.rcSearchProduct.visibility = View.VISIBLE

        }
        binding.backPress.setOnClickListener {
            finish()
        }

        binding.submit.setOnClickListener {
            val intent = Intent(this@CompanyListActiity, LadgerListingActivity::class.java)
            intent.putExtra("ACC_ID", comId)
            intent.putExtra("COM_NAME", comName)
            intent.putExtra("fromdate", binding.fromDate.text.toString())
            intent.putExtra("todate", binding.toDate.text.toString())
            Utility.startActivityWithLeftToRightAnimation(this@CompanyListActiity, intent)

        }




        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                Log.d("sdfsdfsdf1", charSequence.toString())

                binding.dateLayout.setVisibility(View.GONE)
                binding.rcSearchProduct.visibility = View.VISIBLE

                setAdapterSearchProduct()
                mSearchCompany.setViewCallback(mContext)
                callSearchProductApi(charSequence.toString())

            }

            override fun afterTextChanged(editable: Editable) {
                val capitalizedText = WordUtils.capitalize(binding.searchEdit.text.toString())
                if (capitalizedText != binding.searchEdit.text.toString()) {
                    binding.searchEdit.addTextChangedListener(object : TextWatcher {
                        var mStart = 0

                        override fun beforeTextChanged(
                            s: CharSequence,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            mStart = start + count
                        }

                        override fun afterTextChanged(s: Editable) {
                            binding.searchEdit.setSelection(mStart)
                            binding.searchEdit.removeTextChangedListener(this)
                        }
                    })

                    binding.searchEdit.setText(capitalizedText)
                }
            }
        })


        binding.fromDate.setOnClickListener {

            val newCalendar = Calendar.getInstance()
            fromDatePickerDialog = DatePickerDialog(this, R.style.datepicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)
                    binding.fromDate.setText(dateFormatter.format(newDate.getTime()))
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(
                    Calendar.DAY_OF_MONTH))
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
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(
                    Calendar.DAY_OF_MONTH))
//            fromDatePickerDialog!!.datePicker.minDate = newCalendar.timeInMillis
            fromDatePickerDialog!!.show()
        }
    }

    fun setAdapterSearchProduct() {
        if (!::mSearchCompany.isInitialized) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val recyclerView = binding.rcSearchProduct
            recyclerView.layoutManager = layoutManager

            mSearchCompany = SearchCompanyAdapter()
            recyclerView.adapter = mSearchCompany
        }

    }

    private fun callSearchProductApi(searchValue: String) {
//        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callSearchData(Prefences.getGST_No(mContext), searchValue)
            .observe(mContext, object : Observer<SearchCompanyList> {
                override fun onChanged(resp: SearchCompanyList?) {
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            if (resp.data!!.size > 0) {
                                binding.rcSearchProduct.visibility = View.VISIBLE
                                binding.liEmptyLayout.visibility = View.GONE
                                mResultSearchData = ArrayList(resp.data)
                                mSearchCompany.setData(mResultSearchData!!)
                            } else {
                                binding.rcSearchProduct.visibility = View.GONE
                                binding.liEmptyLayout.visibility = View.VISIBLE
                            }
                        }

                    }
                }

            })

    }

    override fun onSearchClick(pId: String, com_name: String) {

        binding.searchEdit.setText("" + com_name)
        binding.dateLayout.setVisibility(View.VISIBLE)
        binding.rcSearchProduct.visibility = View.GONE

        comId = pId
        comName = com_name

//        val intent = Intent(this@CompanyListActiity, DateSelectionActivity::class.java)
//        intent.putExtra("ACC_ID",pId)
//        intent.putExtra("COM_NAME",com_name)
//        Utility.startActivityWithLeftToRightAnimation(this@CompanyListActiity,intent)

    }
}
