package com.accountapp.accounts.ui.companies

import android.content.Intent
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.R
import com.accountapp.accounts.adapter.MyCompanyAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityMyCompaniesBinding
import com.accountapp.accounts.model.response.CompanyListingResponse
import com.accountapp.accounts.model.response.DataItemComListing
import com.accountapp.accounts.ui.home.HomeActivity
import com.accountapp.accounts.ui.ladgerList.ListViewModel
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import kotlinx.android.synthetic.main.activity_my_companies.*

class MyCompaniesActivity : BaseActivity() , MyCompanyAdapter.SelectionCallback {


    lateinit var binding: ActivityMyCompaniesBinding
    val mContext by lazy { this@MyCompaniesActivity }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(ListViewModel::class.java) }

    lateinit var mMyCompany: MyCompanyAdapter
    var mResultLedgerData: MutableList<DataItemComListing>? = null

    override fun initUI() {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_my_companies)
        setToolbarWithBackIcon(binding.includedToolbar.findViewById(R.id.toolbar),"My Companies")

        fabAdd.setOnClickListener {
            if (isInternetAvailable(binding.root, mContext)) {
                val intent = Intent(mContext, AddCompanyActivity::class.java)
                Utility.startActivityWithLeftToRightAnimation(mContext, intent)
            }
        }

        setAdapterCompany()
        mMyCompany.setViewCallback(this)
        getMyCompanyApi(Prefences.getUserMobile(mContext))

    }

    fun setAdapterCompany() {
        if (!::mMyCompany.isInitialized) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val recyclerView = binding.rcMyCompany
            recyclerView.layoutManager = layoutManager

            mMyCompany = MyCompanyAdapter()
            recyclerView.adapter = mMyCompany
        }

    }

    private fun getMyCompanyApi(mobileNo: String?) {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)

        mViewModel.callCompanyList(mobileNo)
            .observe(mContext, object : Observer<CompanyListingResponse> {
                override fun onChanged(resp: CompanyListingResponse?) {
                    showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            if (resp.data!!.size > 0) {
                                mResultLedgerData = ArrayList(resp.data)
                                mMyCompany.setData(mResultLedgerData!!)

                            }
                        }

                    }
                }

            })
    }

    override fun onSelectCompany(company: String,gst: String) {
        Prefences.setCompany(mContext,company)
        Prefences.setGST_No(mContext,gst)
        val intent = Intent(this@MyCompaniesActivity, HomeActivity::class.java)
        Utility.startActivityWithLeftToRightAnimation(this@MyCompaniesActivity,intent)
        finish()
    }

}
