package com.accountapp.accounts.ui.ladgerList

import android.app.ProgressDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.R
import com.accountapp.accounts.adapter.LedgerCompanyAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityLadgerListingBinding
import com.accountapp.accounts.model.response.DataItemLadger
import com.accountapp.accounts.model.response.LadgerListResponse
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LadgerListingActivity : BaseActivity(), LedgerCompanyAdapter.TotalCallback {

    override fun onTotalCallback(crTotal: Double, drTotal: Double, balTotal: Double) {

    }

    lateinit var binding: ActivityLadgerListingBinding
    val mContext by lazy { this@LadgerListingActivity }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(ListViewModel::class.java) }

    var crTotal = 0.0
    var drTotal = 0.0
    var balTotal = 0.0
    var totalBlalance = 0.0
    var x = 0
    var pDialog: ProgressDialog? = null
    var downloadIdTwelve: Int = 0

    lateinit var mLedgerCompany: LedgerCompanyAdapter
    var mResultLedgerData: MutableList<DataItemLadger>? = null
    private var dirPath: String? = null
    var ledgerDate: String = ""
    var currentDate:String=""
    var currentYear:String=""
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US) //2018-10-18 MM-dd-yyyy

    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ladger_listing)
        setToolbarWithBackIcon(
            binding.includedToolbar.findViewById(R.id.toolbar), intent.getStringExtra("COM_NAME")!!
        )

        var companyName = intent.getStringExtra("COM_NAME")
        var ACC_ID = intent.getStringExtra("ACC_ID")
        var fromDate = intent.getStringExtra("fromdate")
        var endDate = intent.getStringExtra("todate")
        var fromDateAPi = intent.getStringExtra("fromdateApi")
        var endDateApi = intent.getStringExtra("todateApi")

        val newDate = Calendar.getInstance()
        currentDate=dateFormatter.format(newDate.getTime())
        currentYear= (Calendar.getInstance().get(Calendar.YEAR)-1).toString()

        if (fromDate.equals("") && endDate.equals("")){
//            ledgerDate="No Date Selected"
            ledgerDate="1-Apr-"+currentYear+" to- "+ currentDate
        }else{
            ledgerDate = "From date- " + fromDate + " to- " + endDate
        }
        setToolbarWithBackIconSubTitle(
            binding.includedToolbar.findViewById(R.id.toolbar),
            ledgerDate
        )

        dirPath = Utility.getRootDirPath(applicationContext)

        var financial_year= Prefences.getSessionFull(mContext)!!.substring (3, 7);

        pDialog = ProgressDialog(this)
        pDialog!!.setMessage("Please wait...");
        pDialog!!.setCancelable(false);

        setAdapterSearchProduct()
        mLedgerCompany.setViewCallback(this)
        getCompanyLadgerData(ACC_ID!!, fromDateAPi!!, endDateApi!!,financial_year)

        binding.fab.setOnClickListener {
            var financial_year= Prefences.getSessionFull(mContext)!!.substring (3, 7);
            if (isInternetAvailable(binding.root, mContext)) {
                callPdfDownlaod(ACC_ID!!, fromDateAPi!!, endDateApi!!, companyName!!,financial_year)
            }
        }
    }

    private fun callPdfDownlaod(
        accId: String,
        fromDate: String,
        endDate: String,
        companyName: String,
        financial_year:String
    ) {

        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callPdffGenerateApi(Prefences.getGST_No(mContext), accId, fromDate, endDate,financial_year)
            .observe(mContext, object : Observer<PDFGeneratorReponse> {
                override fun onChanged(resp: PDFGeneratorReponse?) {

                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            //PRDownloader.download(resp.pdfUrl)

                            downloadIdTwelve =
                                PRDownloader.download(
                                    "" + resp.pdf_url,
                                    dirPath,
                                    "" + companyName + ".pdf"
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
//                                            Utility.showSnackBar(binding.root, "Pdf download successfully.")
                                            Toast.makeText(
                                                    applicationContext,
                                                "Pdf download successfully.",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            Utility.openPdfWithIntent(
                                                dirPath + "/" + companyName + ".pdf",
                                                mContext
                                            )
                                        }

                                        override fun onError(error: com.downloader.Error) {
                                            Log.d("pdfStatus", "error:" + error)
                                        }
                                    })

                        } else {
                        }
                    } else {
                        Utility.showSnackBar(binding.root, "Some error, Please try gain.")
                    }
                }

            })

    }

    private fun getCompanyLadgerData(accId: String, fromDate: String, endDate: String,financial_year:String) {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)

        mViewModel.callLadgerList(Prefences.getGST_No(mContext), accId, fromDate, endDate,financial_year)
            .observe(mContext, object : Observer<LadgerListResponse> {
                override fun onChanged(resp: LadgerListResponse?) {
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
                                mLedgerCompany.setData(mResultLedgerData!!)


                                while (x < resp.data.size) {
                                    println(x)

                                    drTotal = drTotal + resp.data.get(x).DEBIT.toDouble()
                                    crTotal = crTotal + resp.data.get(x).CREDIT.toDouble()
                                    balTotal = balTotal + resp.data.get(x).BALANCE.toDouble()

                                    x++ // Same as x += 1
                                }
                                binding.txtDrTotal.setText("" +String.format("%.2f", drTotal))
                                binding.txtCrTotal.setText("" + String.format("%.2f",crTotal))
                                if (drTotal > crTotal) {
                                    totalBlalance = drTotal - crTotal
                                    binding.txtBalTotal.setText("" + String.format("%.2f",totalBlalance) + "(Dr)")
                                } else {
                                    totalBlalance = crTotal - drTotal
                                    binding.txtBalTotal.setText("" + String.format("%.2f",totalBlalance )+ "(Cr)")
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

    fun setAdapterSearchProduct() {
        if (!::mLedgerCompany.isInitialized) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val recyclerView = binding.rcSearchProduct
            recyclerView.layoutManager = layoutManager

            mLedgerCompany = LedgerCompanyAdapter()
            recyclerView.adapter = mLedgerCompany
        }

    }


}
