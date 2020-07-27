package com.accountapp.accounts.ui.sundryDrCr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.R
import com.accountapp.accounts.adapter.TrialBalanceAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivitySundryCrDrActivitiesBinding
import com.accountapp.accounts.databinding.ActivityTrialBalanceListingActivitesBinding
import com.accountapp.accounts.model.response.DataItemTB
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.model.response.TrialBalanceRespone
import com.accountapp.accounts.ui.ladgerList.ListViewModel
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SundryCrDrActivities : BaseActivity() {
    lateinit var binding: ActivitySundryCrDrActivitiesBinding
    val mContext by lazy { this@SundryCrDrActivities }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(SundryViewModel::class.java) }

    lateinit var mTrialBalanceCompany: TrialBalanceAdapter
    var mResultLedgerData: MutableList<DataItemTB>? = null
    private var dirPath: String? = null
    var downloadIdTwelve: Int = 0
    var crTotal = 0.0
    var drTotal = 0.0
    var balTotal = 0.0
    var totalBlalance = 0.0
    var x = 0
    var selectedDate: String = ""
    var currentDate:String=""
    var currentYear:String=""
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US) //2018-10-18 MM-dd-yyyy

    override fun initUI() {

        binding= DataBindingUtil.setContentView(this, R.layout.activity_sundry_cr_dr_activities)
     //   var fromDate = intent.getStringExtra("fromdate")
        var endDate = intent.getStringExtra("todate")
        var type = intent.getStringExtra("type")
        var endDateForApi=intent.getStringExtra("todateForApi")

        dirPath = Utility.getRootDirPath(applicationContext)

        val newDate = Calendar.getInstance()
        currentDate=dateFormatter.format(newDate.getTime())
        currentYear= (Calendar.getInstance().get(Calendar.YEAR)-1).toString()


        if (type.equals("CR")) {
            setToolbarWithBackIcon(
                binding.includedToolbar.findViewById(R.id.toolbar),
                "Sundry Creditors"
            )
            getSundryCr("", endDateForApi)
        }
        else{
            getSundryDr("", endDateForApi)
            setToolbarWithBackIcon(
                binding.includedToolbar.findViewById(R.id.toolbar),
                "Sundry Debtors"
            )
        }

        if ( endDate.equals("")){
//            selectedDate="1-Apr-"+currentYear+" to- "+ currentDate
            selectedDate = "As on Date " + currentDate
        }else{
            selectedDate = "As on Date " + endDate
        }
        setToolbarWithBackIconSubTitle(
            binding.includedToolbar.findViewById(R.id.toolbar),
            selectedDate
        )
        setAdapterSundryCrDrBalance()

            binding.fab.setOnClickListener {
                if (isInternetAvailable(binding.root, mContext)) {
                    if (type.equals("CR"))
                        callSundryCrPdfDownlaod("", endDateForApi)
                    else
                        callSundryDrPdfDownlaod("", endDateForApi)
                }
            }
    }


    fun setAdapterSundryCrDrBalance() {
        if (!::mTrialBalanceCompany.isInitialized) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val recyclerView = binding.rcSearchProduct
            recyclerView.layoutManager = layoutManager

            mTrialBalanceCompany = TrialBalanceAdapter()
            recyclerView.adapter = mTrialBalanceCompany
        }

    }

    private fun getSundryCr(fromDate: String, endDate: String) {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)

        mViewModel.callSundryCredator(Prefences.getGST_No(mContext), fromDate, endDate)
            .observe(mContext, object : Observer<TrialBalanceRespone> {
                override fun onChanged(resp: TrialBalanceRespone?) {
                    showLoadingView(
                        false,
                        binding.loadingView.loadingIndicator,
                        binding.loadingView.container
                    )
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            if (resp.data!!.size > 0) {
                                binding.rcSearchProduct.visibility = View.VISIBLE
                                binding.liEmptyLayout.visibility = View.GONE
                                binding.topStrip.visibility = View.VISIBLE
                                binding.bottomStrip.visibility = View.VISIBLE
                                mResultLedgerData = ArrayList(resp.data)
                                mTrialBalanceCompany.setData(mResultLedgerData!!)


                                while (x < resp.data.size) {
                                    println(x)

                                    if (resp.data.get(x).dr == "") {
                                        drTotal = drTotal + 0.0
                                    } else {
                                        drTotal = drTotal + resp.data.get(x).dr.toDouble()
                                    }
                                    if (resp.data.get(x).cr == "") {
                                        crTotal = crTotal + 0.0
                                    } else {
                                        crTotal = crTotal + resp.data.get(x).cr.toDouble()
                                    }
                                    //  balTotal = balTotal + resp.data.get(x).BALANCE.toInt()

                                    x++ // Same as x += 1
                                }
                                binding.txtDrTotal.setText("" + String.format("%.2f", drTotal))
                                binding.txtCrTotal.setText("" + String.format("%.2f", crTotal))
                                if (drTotal > crTotal) {
                                    totalBlalance = drTotal - crTotal
                                    // binding.txtBalTotal.setText(""+totalBlalance+"(Dr)")
                                } else {
                                    totalBlalance = crTotal - drTotal
                                    // binding.txtBalTotal.setText(""+totalBlalance+"(Cr)")
                                }

                            } else {
                                binding.rcSearchProduct.visibility = View.GONE
                                binding.liEmptyLayout.visibility = View.VISIBLE
                                binding.topStrip.visibility = View.GONE
                                binding.bottomStrip.visibility = View.GONE
                            }
                        } else {
                            Utility.showSnackBar(binding.root, ""+resp.msg)
                        }

                    }
                }

            })
    }

    private fun getSundryDr(fromDate: String, endDate: String) {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)

        mViewModel.callSundryDebator(Prefences.getGST_No(mContext), fromDate, endDate)
//        mViewModel.callTrialBalanceList("03demodemo",fromDate,endDate)
            .observe(mContext, object : Observer<TrialBalanceRespone> {
                override fun onChanged(resp: TrialBalanceRespone?) {
                    showLoadingView(
                        false,
                        binding.loadingView.loadingIndicator,
                        binding.loadingView.container
                    )
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            if (resp.data!!.size > 0) {
                                binding.rcSearchProduct.visibility = View.VISIBLE
                                binding.liEmptyLayout.visibility = View.GONE
                                binding.topStrip.visibility = View.VISIBLE
                                binding.bottomStrip.visibility = View.VISIBLE
                                mResultLedgerData = ArrayList(resp.data)
                                mTrialBalanceCompany.setData(mResultLedgerData!!)


                                while (x < resp.data.size) {
                                    println(x)

                                    if (resp.data.get(x).dr == "") {
                                        drTotal = drTotal + 0.0
                                    } else {
                                        drTotal = drTotal + resp.data.get(x).dr.toDouble()
                                    }
                                    if (resp.data.get(x).cr == "") {
                                        crTotal = crTotal + 0.0
                                    } else {
                                        crTotal = crTotal + resp.data.get(x).cr.toDouble()
                                    }

                                    x++ // Same as x += 1
                                }
                                binding.txtDrTotal.setText("" + String.format("%.2f", drTotal))
                                binding.txtCrTotal.setText("" + String.format("%.2f", crTotal))
                                if (drTotal > crTotal) {
                                    totalBlalance = drTotal - crTotal
                                    // binding.txtBalTotal.setText(""+totalBlalance+"(Dr)")
                                } else {
                                    totalBlalance = crTotal - drTotal
                                }

                            } else {
                                binding.rcSearchProduct.visibility = View.GONE
                                binding.liEmptyLayout.visibility = View.VISIBLE
                                binding.topStrip.visibility = View.GONE
                                binding.bottomStrip.visibility = View.GONE
                            }
                        } else {
                            Utility.showSnackBar(binding.root, ""+resp.msg)
                        }

                    }
                }

            })
    }


    private fun callSundryCrPdfDownlaod(fromDate: String, endDate: String) {

        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callSundryCredatorPDF(Prefences.getGST_No(mContext), fromDate, endDate)
            .observe(mContext, object : Observer<PDFGeneratorReponse> {
                override fun onChanged(resp: PDFGeneratorReponse?) {

                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            downloadIdTwelve =
                                PRDownloader.download(
                                    "" + resp.pdf_url,
                                    dirPath,
                                    "SundryCreditor-" + Prefences.getGST_No(mContext) + ".pdf"
                                )
                                    .build()
                                    .setOnStartOrResumeListener {
                                        Log.d("pdfStatus", "resume")
                                    }
                                    .setOnCancelListener {
                                    }
                                    .setOnProgressListener { progress ->
                                    }
                                    .start(object : OnDownloadListener {
                                        override fun onDownloadComplete() {
                                            Log.d("pdfStatus", "complete")
                                            showLoadingView(
                                                false,
                                                binding.loadingView.loadingIndicator,
                                                binding.loadingView.container
                                            )
                                            //Utility.showSnackBar(binding.root,"Pdf download successfully.")

                                            Toast.makeText(
                                                applicationContext, "Pdf download successfully.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Utility.openPdfWithIntent(
                                                dirPath + "/" + "SundryCreditor-" + Prefences.getGST_No(
                                                    mContext
                                                ) + ".pdf", mContext
                                            )

                                        }

                                        override fun onError(error: com.downloader.Error) {
                                            Log.d("pdfStatus", "error:" + error)
                                        }
                                    })

//                            DownloadFile(pDialog!!,mContext).execute(""+resp.pdfUrl, companyName+".pdf")
                        } else {
                        }
                    } else {
                       // Utility.showSnackBar(binding.root, "Some error, Please try gain.")
                    }
                }

            })

    }

    private fun callSundryDrPdfDownlaod(fromDate: String, endDate: String) {

        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callSundryDebatorPDF(Prefences.getGST_No(mContext), fromDate, endDate)
//        mViewModel.callPdffGenerateTrialBalance("03demodemo",fromDate,endDate)
            .observe(mContext, object : Observer<PDFGeneratorReponse> {
                override fun onChanged(resp: PDFGeneratorReponse?) {

                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            downloadIdTwelve =
                                PRDownloader.download(
                                    "" + resp.pdf_url,
                                    dirPath,
                                    "SundryDebtor-" + Prefences.getGST_No(mContext) + ".pdf"
                                )
                                    .build()
                                    .setOnStartOrResumeListener {
                                        Log.d("pdfStatus", "resume")
                                    }
                                    .setOnCancelListener {
                                    }
                                    .setOnProgressListener { progress ->
                                    }
                                    .start(object : OnDownloadListener {
                                        override fun onDownloadComplete() {
                                            Log.d("pdfStatus", "complete")
                                            showLoadingView(
                                                false,
                                                binding.loadingView.loadingIndicator,
                                                binding.loadingView.container
                                            )
                                            //Utility.showSnackBar(binding.root,"Pdf download successfully.")

                                            Toast.makeText(
                                                applicationContext, "Pdf download successfully.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Utility.openPdfWithIntent(
                                                dirPath + "/" + "SundryDebtor-" + Prefences.getGST_No(
                                                    mContext
                                                ) + ".pdf", mContext
                                            )

                                        }

                                        override fun onError(error: com.downloader.Error) {
                                            Log.d("pdfStatus", "error:" + error)
                                        }
                                    })

                        } else {
                        }
                    } else {
                     //   Utility.showSnackBar(binding.root, "Some error, Please try gain.")
                    }
                }

            })

    }

}
