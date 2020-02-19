package com.accountapp.accounts.ui.ladgerList

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
import com.accountapp.accounts.adapter.LedgerCompanyAdapter
import com.accountapp.accounts.adapter.TrialBalanceAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityLadgerListingBinding
import com.accountapp.accounts.databinding.ActivityTrialBalanceListingActivitesBinding
import com.accountapp.accounts.model.response.*
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrialBalanceListingActivites : BaseActivity() {

    lateinit var binding: ActivityTrialBalanceListingActivitesBinding
    val mContext by lazy { this@TrialBalanceListingActivites }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(ListViewModel::class.java) }

    lateinit var mTrialBalanceCompany: TrialBalanceAdapter
    var mResultLedgerData: MutableList<DataItemTB>? = null
    private var dirPath: String? = null
    var downloadIdTwelve:Int = 0
    var crTotal = 0.0
    var drTotal = 0.0
    var balTotal = 0.0
    var totalBlalance=0.0
    var x = 0
    var selectedDate: String = ""
    var currentDate:String=""
    var currentYear:String=""
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US) //2018-10-18 MM-dd-yyyy

    override fun initUI() {

        binding= DataBindingUtil.setContentView(this, R.layout.activity_trial_balance_listing_activites)
        setToolbarWithBackIcon(
            binding.includedToolbar.findViewById(R.id.toolbar), "Trial Balance")


        val newDate = Calendar.getInstance()
        currentDate=dateFormatter.format(newDate.getTime())
        currentYear= (Calendar.getInstance().get(Calendar.YEAR)-1).toString()

        var endDate=intent.getStringExtra("todate")
        var endDateForApi=intent.getStringExtra("todateForApi")

        dirPath = Utility.getRootDirPath(applicationContext)

        if ( endDate.equals("")){
//            selectedDate="No Date Selected"
            selectedDate="1-Apr-"+currentYear+" to- "+ currentDate

        }else{
            selectedDate = "As on Date " + endDate
        }
        setToolbarWithBackIconSubTitle(
            binding.includedToolbar.findViewById(R.id.toolbar),
            selectedDate
        )

        setAdapterTrialBalance()
      //  mLedgerCompany.setViewCallback(this)
        getCompanyTrialBalanceData("",endDateForApi)

        binding.fab.setOnClickListener {
            if (isInternetAvailable(binding.root, mContext)) {
                callPdfDownlaod("", endDateForApi)
            }
        }

    }
    fun setAdapterTrialBalance() {
        if (!::mTrialBalanceCompany.isInitialized) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val recyclerView = binding.rcSearchProduct
            recyclerView.layoutManager = layoutManager

            mTrialBalanceCompany = TrialBalanceAdapter()
            recyclerView.adapter = mTrialBalanceCompany
        }

    }

    private fun getCompanyTrialBalanceData(fromDate: String,endDate: String) {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)

        mViewModel.callTrialBalanceList(Prefences.getGST_No(mContext),fromDate,endDate)
//        mViewModel.callTrialBalanceList("03demodemo",fromDate,endDate)
            .observe(mContext, object : Observer<TrialBalanceRespone> {
                override fun onChanged(resp: TrialBalanceRespone?) {
                    showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            if (resp.data!!.size > 0) {
                                binding.rcSearchProduct.visibility= View.VISIBLE
                                binding.liEmptyLayout.visibility= View.GONE
                                binding.topStrip.visibility= View.VISIBLE
                                binding.bottomStrip.visibility= View.VISIBLE
                                mResultLedgerData = ArrayList(resp.data)
                                mTrialBalanceCompany.setData(mResultLedgerData!!)


                                while (x < resp.data.size) {
                                    println(x)

                                    if (resp.data.get(x).dr==""){
                                        drTotal =drTotal+0.0
                                    }else{
                                        drTotal = drTotal + resp.data.get(x).dr.toDouble()
                                    }
                                    if (resp.data.get(x).cr==""){
                                        crTotal=crTotal+0.0
                                    }else {
                                        crTotal = crTotal + resp.data.get(x).cr.toDouble()
                                    }
                                  //  balTotal = balTotal + resp.data.get(x).BALANCE.toInt()

                                    x++ // Same as x += 1
                                }
                                binding.txtDrTotal.setText("" +String.format("%.2f", drTotal) )
                                binding.txtCrTotal.setText(""+String.format("%.2f",crTotal))
                                if (drTotal>crTotal){
                                    totalBlalance=drTotal-crTotal
                                   // binding.txtBalTotal.setText(""+totalBlalance+"(Dr)")
                                }else{
                                    totalBlalance=crTotal-drTotal
                                   // binding.txtBalTotal.setText(""+totalBlalance+"(Cr)")
                                }

                            } else {
                                binding.rcSearchProduct.visibility= View.GONE
                                binding.liEmptyLayout.visibility= View.VISIBLE
                                binding.topStrip.visibility= View.GONE
                                binding.bottomStrip.visibility= View.GONE
                            }
                        }else{
                            Utility.showSnackBar(binding.root, "No record found")
                        }

                    }
                }

            })
    }

    private fun callPdfDownlaod(fromDate: String,endDate: String) {

        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callPdffGenerateTrialBalance(Prefences.getGST_No(mContext),fromDate,endDate)
//        mViewModel.callPdffGenerateTrialBalance("03demodemo",fromDate,endDate)
            .observe(mContext, object : Observer<PDFGeneratorReponse> {
                override fun onChanged(resp: PDFGeneratorReponse?) {

                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            //PRDownloader.download(resp.pdfUrl)

                            downloadIdTwelve =
                                PRDownloader.download(""+resp.pdf_url, dirPath, "TrialBalance-"+Prefences.getGST_No(mContext)+".pdf")
                                    .build()
                                    .setOnStartOrResumeListener {
                                        Log.d("pdfStatus","resume")
                                    }
                                    .setOnCancelListener {
                                    }
                                    .setOnProgressListener { progress ->
                                    }
                                    .start(object : OnDownloadListener {
                                        override fun onDownloadComplete() {
                                            Log.d("pdfStatus","complete")
                                            showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                                            //Utility.showSnackBar(binding.root,"Pdf download successfully.")

                                            Toast.makeText(applicationContext,"Pdf download successfully.",
                                                Toast.LENGTH_LONG).show()
                                            Utility.openPdfWithIntent( dirPath+"/" + "TrialBalance-"+Prefences.getGST_No(mContext) + ".pdf",mContext)
//                                            val file = File(dirPath, ""+companyName+".pdf")
//                                            val path = Uri.fromFile(file)
//                                            val pdfOpenintent = Intent(Intent.ACTION_VIEW)
//                                            pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                            pdfOpenintent.setDataAndType(path, "application/pdf")
//                                            try {
//                                                startActivity(pdfOpenintent)
//                                            } catch (e: ActivityNotFoundException) {
//
//                                            }

                                        }

                                        override fun onError(error: com.downloader.Error) {
                                            Log.d("pdfStatus","error:"+error)
                                        }
                                    })

//                            DownloadFile(pDialog!!,mContext).execute(""+resp.pdfUrl, companyName+".pdf")
                        } else {
                        }
                    } else {
                        Utility.showSnackBar(binding.root,"Some error, Please try gain.")
                    }
                }

            })

    }

}
