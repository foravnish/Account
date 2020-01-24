package com.accountapp.accounts.ui.profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil

import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.databinding.ActivityProfileBinding
import com.accountapp.accounts.ui.changePassword.ChangePasswordActivity
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragemnt : BaseFragment() {


    lateinit var binding: ActivityProfileBinding

    val mContext by lazy { context }

    companion object {
        val TAG = "FragmentProfile"

        fun newInstance(args: Bundle?): ProfileFragemnt {
            val fragment = ProfileFragemnt()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_profile, container, false)



        binding.txtName.setText(""+ Prefences.getUserName(mContext!!))
        binding.mobileNo.setText("Mobile No: "+ Prefences.getUserMobile(mContext!!))
        binding.emailId.setText("Email ID: "+ Prefences.getUserEmailId(mContext!!))
        binding.company.setText("Company: "+ Prefences.getCompany(mContext!!))
        binding.gstNo.setText("GST: "+ Prefences.getGST_No(mContext!!))
        binding.addresses.setText("Address: "+ Prefences.getAddress(mContext!!))
        binding.city.setText("City: "+ Prefences.getCity(mContext!!))


        binding.logout.setOnClickListener {

            Prefences.resetUserData(mContext!!)
            val intent = Intent(activity, LoginActivity::class.java)
            Utility.startActivityWithLeftToRightAnimation(activity,intent)
            activity!!.finishAffinity()
        }


        binding.txtChangePasword.setOnClickListener {
            Utility.startActivityWithLeftToRightAnimation(activity, Intent(activity, ChangePasswordActivity::class.java))

        }

        return binding.root

    }
    override fun getFragmentTag(): String {

        return TAG
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                Log.d("sfsdfdsfsdfsd","true")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
