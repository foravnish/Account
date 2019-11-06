package com.accountapp.accounts.callback

import android.os.Bundle

interface OnFragmentInteractionListener {
    fun onFragmentInteraction(tag: String)

    fun showFragment(tag: String, args: Bundle?, addToBackStack: Boolean, replace: Boolean)

    fun popFrag(tag: String)
}