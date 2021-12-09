package com.accountapp.accounts.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.R
import com.accountapp.accounts.adapter.FYListAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityEditProfileBinding
import com.accountapp.accounts.databinding.ActivityFyYearBinding
import com.accountapp.accounts.databinding.ActivityHomeBinding
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.ui.home.HomeActivity
import com.accountapp.accounts.ui.signup.SignupViewModel
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.Utility.Companion.getLastFiveFinancialYear
import com.accountapp.accounts.utils.ValidationHelper

class FinancialYearActivityActivity : BaseActivity() {
    val mContext by lazy { this@FinancialYearActivityActivity }
    lateinit var binding: ActivityFyYearBinding
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(SignupViewModel::class.java) }
    var mFromDT = ""
    var mToDT = ""

    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fy_year)
        setToolbarWithBackIcon(binding.includedToolbar.findViewById(R.id.toolbar), "Financial Year")


        binding.rvFYList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val mAdapter = FYListAdapter(Utility.getLastFiveFinancialYear()) {
            mFromDT = getLastFiveFinancialYear()[it].fromDt
            mToDT = getLastFiveFinancialYear()[it].toDt
            Prefences.setSessionFull(mContext,getLastFiveFinancialYear()[it].fy)
            Prefences.setSession(mContext,getLastFiveFinancialYear()[it].fy.substring(5,7)+""+getLastFiveFinancialYear()[it].fy.substring(10,12))
            val intent = Intent(this@FinancialYearActivityActivity, HomeActivity::class.java)
            Utility.startActivityWithLeftToRightAnimation(this@FinancialYearActivityActivity,intent)
            finishAffinity()
        }
       binding.rvFYList.adapter = mAdapter

    }


}
