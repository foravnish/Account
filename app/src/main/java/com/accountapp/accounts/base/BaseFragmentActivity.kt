package com.accountapp.accounts.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.accountapp.accounts.callback.OnFragmentInteractionListener

abstract open class BaseFragmentActivity : BaseActivity() , OnFragmentInteractionListener {
    override fun initUI() {


    }

    protected abstract fun getFragmentContainer(): Int

    fun loadFragments(frag: BaseFragment, addToBackStack: Boolean, replace: Boolean, fragmentName: String) {
        if (supportFragmentManager != null && !isFinishing) {
            if (addToBackStack) {
                if (replace) {
                    supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(getFragmentContainer(), frag, frag.getFragmentTag()).commit()
                } else {
                    supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).add(getFragmentContainer(), frag, frag.getFragmentTag()).commit()
                }

            } else {
                supportFragmentManager.beginTransaction().replace(getFragmentContainer(), frag, frag.getFragmentTag()).commitAllowingStateLoss()
            }
            supportFragmentManager.executePendingTransactions()
        }
    }
}
