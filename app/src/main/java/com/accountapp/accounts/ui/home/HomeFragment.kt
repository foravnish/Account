package com.accountapp.accounts.ui.home


import android.app.AlertDialog
import android.content.DialogInterface
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
import com.accountapp.accounts.databinding.FragmentHomeNewBinding
import com.accountapp.accounts.ui.fragment.LedgerFragment
import com.accountapp.accounts.ui.fragment.TrailBalanceFragment
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.ui.sundryDrCr.SundryCredatorFragment
import com.accountapp.accounts.ui.sundryDrCr.SundryDebatorsFragment


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {
    override fun getFragmentTag(): String {
        return TAG

    }

    val TAG = "FragmentHome"

    companion object {
        val TAG = "FragmentHome"

        fun newInstance(args: Bundle?): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var binding: FragmentHomeNewBinding
    val mContext by lazy { context }
    val mViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_new, container, false)
        binding.txtname.setText(""+ Prefences.getCompany(mContext))
        binding.txtDashboardDate.setText(""+ Utility.getCurrentDate("dd MMM yyyy"))
        binding.txtFyear.setText(""+ Prefences.getSessionFull(mContext))

//        binding.txtComName.setText(""+Prefences.getCompany(mContext)+"\n"+Prefences.getGST_No(mContext))

//        binding.btnLogout.setOnClickListener {
//            val intent = Intent(activity, LoginActivity::class.java)
//            Utility.startActivityWithLeftToRightAnimation(activity,intent)
//
//        }
//        binding.btnMyCom.setOnClickListener {
//            if (this!!.mContext?.let { it1 -> isInternetAvailable(binding.root, it1) }!!) {
//                val intent = Intent(activity, MyCompaniesActivity::class.java)
//                Utility.startActivityWithLeftToRightAnimation(activity, intent)
//            }
//        }
//        binding.myProfile.setOnClickListener {
//            val intent = Intent(activity, ProfileFragemnt::class.java)
//            Utility.startActivityWithLeftToRightAnimation(activity,intent)
//        }
//
//        binding.logout.setOnClickListener {
//            openLogoutDialog()
//
//        }

        binding.clickLedger.setOnClickListener {

            if (isInternetAvailable(binding.root,mContext!!)) {
                val intent = Intent(activity, LedgerFragment::class.java)
                Utility.startActivityWithLeftToRightAnimation(activity, intent)
            }
        }
        binding.clickTrailBal.setOnClickListener {
            if (isInternetAvailable(binding.root,mContext!!)) {
                val intent = Intent(activity, TrailBalanceFragment::class.java)
                Utility.startActivityWithLeftToRightAnimation(activity, intent)
            }

        }
        binding.clickSudrCr.setOnClickListener {
            if (isInternetAvailable(binding.root,mContext!!)) {
                val intent = Intent(activity, SundryCredatorFragment::class.java)
                Utility.startActivityWithLeftToRightAnimation(activity, intent)
            }

        }
        binding.clickSudDr.setOnClickListener {
            if (isInternetAvailable(binding.root,mContext!!)) {
                val intent = Intent(activity, SundryDebatorsFragment::class.java)
                Utility.startActivityWithLeftToRightAnimation(activity, intent)
            }

        }
        binding.clickSaleBook.setOnClickListener {

        }
        binding.clickPurchaseBook.setOnClickListener {

        }
        binding.clickItemLedger.setOnClickListener {

        }
        binding.clickStockSummary.setOnClickListener {

        }

        return binding.root

    }

    private fun openLogoutDialog() {
        
        val dialogBuilder = AlertDialog.Builder(context)

        // set message of alert dialog
        dialogBuilder.setMessage("Do you want to Logout?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()

                Prefences.resetUserData(mContext!!)
                val intent = Intent(mContext, LoginActivity::class.java)
                Utility.startActivityWithLeftToRightAnimation(activity,intent)
                activity!!.finishAffinity()

            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        alert.show()



    }


}
