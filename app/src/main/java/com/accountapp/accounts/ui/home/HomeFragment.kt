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
import com.accountapp.accounts.ui.companies.MyCompaniesActivity
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.databinding.FragmentHomeBinding
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.ui.profile.ProfileFragemnt


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

    lateinit var binding: FragmentHomeBinding
    val mContext by lazy { context }
    val mViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.txtname.setText(""+ Prefences.getCompany(mContext))
//        binding.txtComName.setText(""+Prefences.getCompany(mContext)+"\n"+Prefences.getGST_No(mContext))

        binding.btnLogout.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            Utility.startActivityWithLeftToRightAnimation(activity,intent)

        }
        binding.btnMyCom.setOnClickListener {
            if (this!!.mContext?.let { it1 -> isInternetAvailable(binding.root, it1) }!!) {
                val intent = Intent(activity, MyCompaniesActivity::class.java)
                Utility.startActivityWithLeftToRightAnimation(activity, intent)
            }
        }
        binding.myProfile.setOnClickListener {
            val intent = Intent(activity, ProfileFragemnt::class.java)
            Utility.startActivityWithLeftToRightAnimation(activity,intent)
        }

        binding.logout.setOnClickListener {
            openLogoutDialog()

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
