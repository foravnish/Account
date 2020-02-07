package com.accountapp.accounts.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.accountapp.accounts.R
import com.accountapp.accounts.utils.Utility


open abstract class BaseActivity : AppCompatActivity(), BaseInterface {
    val TAG_MESSAGE = "OkHttp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initUI()

    }

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    fun setToolbarWithBackIcon(toolbar: Toolbar, title: String) {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
    fun setToolbarWithBackIconSubTitle(toolbar: Toolbar, subTitle: String) {
        setSupportActionBar(toolbar)
        supportActionBar!!.subtitle = subTitle
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setBackIcon(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun isInternetAvailable(parentLayout: View?,context: Context): Boolean{
        if (Utility.isNetworkAvailable(context))
            return true
        else {
            Utility.showSnackBar(parentLayout, "" + getString(R.string.err_check_interner))
            return false
        }
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

    fun showServerErrorSnackbar(parentLayout: View?) {
        Utility.showSnackBar(parentLayout, "A server error has occurred")
    }

    fun showmssg(parentLayout: View?,msg:String) {
        Utility.showSnackBar(parentLayout, msg)
    }

    fun showToastmssg(context: Context,msg:String) {

        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()

    }


//    override fun onResume() {
//        super.onResume()
//        Alerts.register(this)
//    }
//
//    override fun onPause() {
//        Alerts.unregister(this)
//        super.onPause()
//    }

}
