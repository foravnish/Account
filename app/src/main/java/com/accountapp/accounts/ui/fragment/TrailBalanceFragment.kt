package com.accountapp.accounts.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.databinding.FragmentTrailBalanceBinding
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.ui.home.HomeViewModel
import com.accountapp.accounts.ui.ladgerList.CompanyListActiity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility

/**
 * A simple [Fragment] subclass.
 */
class TrailBalanceFragment : BaseFragment() {

    override fun getFragmentTag(): String {
        return TAG
    }

    val TAG = "TrailBalanceFragment"

    companion object {
        val TAG = "TrailBalanceFragment"

        fun newInstance(args: Bundle?): TrailBalanceFragment {
            val fragment = TrailBalanceFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var binding: FragmentTrailBalanceBinding
    val mContext by lazy { context }
    val mViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trail_balance, container, false)
        binding.toolbar.title="Trail Balance"
        binding.btnGetCom!!.setOnClickListener(View.OnClickListener {
            callReadCompanyApi()

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
