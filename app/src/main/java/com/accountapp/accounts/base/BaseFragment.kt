package com.accountapp.accounts.base


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.accountapp.accounts.R
import com.accountapp.accounts.callback.OnFragmentInteractionListener
import com.accountapp.accounts.utils.Utility
import com.ithe1percent.ithe1.utils.Lg

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {

    abstract fun getFragmentTag(): String

    lateinit var mListener: OnFragmentInteractionListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
            mListener.onFragmentInteraction(getFragmentTag())
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener") as Throwable
        }
    }
    override fun onResume() {
        if (getFragmentTag() != null) {
            Lg.v(getFragmentTag(), "onResume")
        }
        super.onResume()
    }

    fun showLoadingView(show: Boolean, loadingView: com.agrawalsuneet.loaderspack.loaders.ClockLoader, view: View) {
        if (show) {
            loadingView.visibility = View.VISIBLE
            view.visibility = View.VISIBLE
        } else {
            loadingView.visibility = View.GONE
            view.visibility = View.GONE
        }

    }

    fun isInternetAvailable(parentLayout: View?,context: Context): Boolean{
        if (Utility.isNetworkAvailable(context))
            return true
        else {
            Utility.showSnackBar(parentLayout, "" + getString(R.string.err_check_interner))
            return false
        }
    }
}
