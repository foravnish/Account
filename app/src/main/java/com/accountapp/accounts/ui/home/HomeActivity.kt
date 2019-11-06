package com.accountapp.accounts.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.base.BaseFragmentActivity
import com.accountapp.accounts.databinding.ActivityHomeBinding
import com.accountapp.accounts.ui.profile.ProfileFragemnt
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
        //setSupportActionBar(binding.toolbar)

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
                                //supportActionBar!!.setTitle("Notifications")
                            }
                            return true
                        }
                        R.id.navigation_ledger -> {
                            return true
                        }
                        R.id.navigation_tb -> {
                            return true
                        }
                        R.id.navigation_profile -> {
                            if (currentlyLoadedFragment != ProfileFragemnt.TAG) {
                                TAG = ProfileFragemnt.TAG
                                currentlyLoadedFragment = TAG

                                showFragment(TAG, null, true, true)
                                //supportActionBar!!.setTitle("Notifications")
                            }
//                            val newFragment = ProfileFragemnt()
//                            val transaction = supportFragmentManager.beginTransaction()
//                            transaction.replace(R.id.container, newFragment)
//                            transaction.addToBackStack(null)
//                            transaction.commit()


//                            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
//                            Utility.startActivityWithLeftToRightAnimation(this@HomeActivity,intent)
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
            ProfileFragemnt.TAG -> {
                frag = ProfileFragemnt.newInstance(args)
            }
//            FragmentSettings.TAG -> {
//                frag = FragmentSettings.newInstance(args)
//            }
//            FragmentNotifications.TAG -> {
//                frag = FragmentNotifications.newInstance(args)
//            }

        }

        return frag
    }


}
