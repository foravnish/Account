package com.accountapp.accounts.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.base.BaseFragmentActivity
import com.accountapp.accounts.databinding.ActivityHomeBinding
import com.accountapp.accounts.ui.fragment.LedgerFragment
import com.accountapp.accounts.ui.fragment.TrailBalanceFragment
import com.accountapp.accounts.ui.sundryDrCr.SundryCredatorFragment
import com.accountapp.accounts.ui.sundryDrCr.SundryDebatorsFragment
import com.accountapp.accounts.utils.Utility
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : BaseFragmentActivity() {
    override fun getFragmentContainer(): Int {
        return R.id.container
    }

    override fun onFragmentInteraction(tag: String) {
    }

    override fun popFrag(tag: String) {
    }

    lateinit var binding: ActivityHomeBinding
    val mContext by lazy { this@HomeActivity }
    var TAG: String = "Tag"
    var currentlyLoadedFragment = ""

    override fun initUI() {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding.toolbar)

        TAG = HomeFragment.TAG
        currentlyLoadedFragment = TAG
        showFragment(TAG, null, true, true)


        val mOnNavigationItemSelectedListener =
            object : BottomNavigationView.OnNavigationItemSelectedListener {

                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.navigation_home -> {
                            if (currentlyLoadedFragment != HomeFragment.TAG) {
                                TAG = HomeFragment.TAG
                                currentlyLoadedFragment = TAG

                                showFragment(TAG, null, true, true)
                                //supportActionBar!!.setTitle("Home")
                            }
                            return true
                        }
                        R.id.navigation_ledger -> {
                            Utility.isLadger=true
                            if (currentlyLoadedFragment != LedgerFragment.TAG) {
                                TAG = LedgerFragment.TAG
                                currentlyLoadedFragment = TAG

                                showFragment(TAG, null, true, true)
                               // supportActionBar!!.setTitle("Ledger")
                            }
                            return true
                        }
                        R.id.navigation_tb -> {
                            Utility.isLadger=false
                            if (currentlyLoadedFragment != TrailBalanceFragment.TAG) {
                                TAG = TrailBalanceFragment.TAG
                                currentlyLoadedFragment = TAG

                                showFragment(TAG, null, true, true)
                                //supportActionBar!!.setTitle("Trail Balance")
                            }
                            return true
                        }
                        R.id.navigation_s_cr -> {
                            if (currentlyLoadedFragment != SundryCredatorFragment.TAG) {
                                TAG = SundryCredatorFragment.TAG
                                currentlyLoadedFragment = TAG

                                showFragment(TAG, null, true, true)
                            }

                            return true
                        }
                        R.id.navigation_s_dr -> {
                            if (currentlyLoadedFragment != SundryDebatorsFragment.TAG) {
                                TAG = SundryDebatorsFragment.TAG
                                currentlyLoadedFragment = TAG

                                showFragment(TAG, null, true, true)
                            }

                            return true
                        }
                    }

                    return false
                }
            }


        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }


    override fun showFragment(tag: String, args: Bundle?, addToBackStack: Boolean, replace: Boolean) {
        if (!isFinishing) {
            getFragmentByTag(tag, args)?.let { loadFragments(it, addToBackStack, replace, tag) }
        }

    }
    protected fun getFragmentByTag(tag: String, args: Bundle?): BaseFragment? {
        var frag: BaseFragment? = null
        when (tag) {
            HomeFragment.TAG -> {
                frag = HomeFragment.newInstance(args)
            }
            LedgerFragment.TAG -> {
                frag = LedgerFragment.newInstance(args)
            }
            TrailBalanceFragment.TAG -> {
                frag = TrailBalanceFragment.newInstance(args)
            }
            SundryCredatorFragment.TAG -> {
                frag = SundryCredatorFragment.newInstance(args)
            }
            SundryDebatorsFragment.TAG -> {
                frag = SundryDebatorsFragment.newInstance(args)
            }

        }

        return frag
    }


}
