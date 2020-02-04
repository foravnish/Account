package com.accountapp.accounts.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.databinding.FragmentLedgerBinding
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.ui.home.HomeViewModel
import com.accountapp.accounts.ui.ladgerList.CompanyListActiity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility

/**
 * A simple [Fragment] subclass.
 */
class LedgerFragment : BaseFragment() {

    override fun getFragmentTag(): String {
        return TAG

    }

    val TAG = "LedgerFragment"

    companion object {
        val TAG = "LedgerFragment"

        fun newInstance(args: Bundle?): LedgerFragment {
            val fragment = LedgerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var binding: FragmentLedgerBinding
    val mContext by lazy { context }
    val mViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ledger, container, false)
        binding.btnGetCom!!.setOnClickListener(View.OnClickListener {
            if (this!!.mContext?.let { it1 -> isInternetAvailable(binding.root, it1) }!!) {
                callReadCompanyApi()
            }

        })


        return binding.root


    }

    private fun callReadCompanyApi() {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callReadCompany(Prefences.getGST_No(mContext!!))
            .observe(this, object : Observer<SignUpResponse> {
                override fun onChanged(resp: SignUpResponse?) {
                    showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            Utility.startActivityWithLeftToRightAnimation(activity,
                                Intent(activity, CompanyListActiity::class.java)
                            )
                        } else {
                        }
                    } else {
                        Utility.showSnackBar(binding.root,"File not Exist, Please connect to Admin")
                    }
                }

            })

    }


}
