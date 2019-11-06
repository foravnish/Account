package com.accountapp.accounts.ui.ladgerList

import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.adapter.LedgerCompanyAdapter
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityLadgerListingBinding
import com.accountapp.accounts.model.response.DataItemLadger
import com.accountapp.accounts.model.response.LadgerListResponse
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.utils.DownloadFile
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.accountapp.accounts.R
import com.downloader.*
import java.io.File
import java.io.IOException
import kotlin.Error
import com.downloader.OnDownloadListener
import com.downloader.Progress
import com.downloader.OnProgressListener
import com.downloader.OnPauseListener
import com.downloader.OnStartOrResumeListener
import com.downloader.PRDownloader
import android.util.Log


class LadgerListingActivity : BaseActivity(), LedgerCompanyAdapter.TotalCallback {

    override fun onTotalCallback(crTotal: Double, drTotal: Double, balTotal: Double) {

    }

    lateinit var binding: ActivityLadgerListingBinding
    val mContext by lazy { this@LadgerListingActivity }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(ListViewModel::class.java) }

    var crTotal = 0.0
    var drTotal = 0.0
    var balTotal = 0.0
    var x = 0
    var pDialog: ProgressDialog? = null
    var downloadIdTwelve:Int = 0

    lateinit var mLedgerCompany: LedgerCompanyAdapter
    var mResultLedgerData: MutableList<DataItemLadger>? = null
    private var dirPath: String? = null

    override fun initUI() {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_ladger_listing)
        setToolbarWithBackIcon(
            binding.includedToolbar.findViewById(R.id.toolbar), intent.getStringExtra("COM_NAME"))

        var companyName=intent.getStringExtra("COM_NAME")
        var ACC_ID=intent.getStringExtra("ACC_ID")
        var fromDate=intent.getStringExtra("fromdate")
        var endDate=intent.getStringExtra("todate")


        dirPath = Utility.getRootDirPath(applicationContext)

        pDialog= ProgressDialog(this)
        pDialog!!.setMessage("Please wait...");
        pDialog!!.setCancelable(false);

        setAdapterSearchProduct()
        mLedgerCompany.setViewCallback(this)
        getCompanyLadgerData(ACC_ID,fromDate,endDate)

        binding.fab.setOnClickListener {
            callPdfDownlaod(ACC_ID,fromDate,endDate,companyName)
        }
    }

    private fun callPdfDownlaod(accId: String,fromDate: String,endDate: String,companyName: String) {

        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callPdffGenerateApi(Prefences.getGST_No(mContext),accId,fromDate,endDate)
            .observe(mContext, object : Observer<PDFGeneratorReponse> {
                override fun onChanged(resp: PDFGeneratorReponse?) {

                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            //PRDownloader.download(resp.pdfUrl)

                            downloadIdTwelve =
                                PRDownloader.download(""+resp.pdf_url, dirPath, ""+companyName+".pdf")
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
                                            Utility.showSnackBar(binding.root,"Pdf download successfully.")
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
                                            Log.d("pdfStatus","error")
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

    private fun getCompanyLadgerData(accId: String,fromDate: String,endDate: String) {
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)

        mViewModel.callLadgerList(Prefences.getGST_No(mContext),accId,fromDate,endDate)
            .observe(mContext, object : Observer<LadgerListResponse> {
                override fun onChanged(resp: LadgerListResponse?) {
                    showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            if (resp.data!!.size > 0) {
                                binding.rcSearchProduct.visibility= View.VISIBLE
                                binding.liEmptyLayout.visibility= View.GONE
                                binding.topStrip.visibility= View.VISIBLE
                                binding.bottomStrip.visibility= View.VISIBLE
                                mResultLedgerData = ArrayList(resp.data)
                                mLedgerCompany.setData(mResultLedgerData!!)


                                while (x < resp.data.size) {
                                    println(x)

                                    drTotal = drTotal + resp.data.get(x).DEBIT.toDouble()
                                    crTotal = crTotal + resp.data.get(x).CREDIT.toDouble()
                                    balTotal = balTotal + resp.data.get(x).BALANCE.toInt()

                                    x++ // Same as x += 1
                                }
                                binding.txtDrTotal.setText("" + drTotal)
                                binding.txtCrTotal.setText(""+crTotal)
                                binding.txtBalTotal.setText(""+balTotal)

                            } else {
                                binding.rcSearchProduct.visibility= View.GONE
                                binding.liEmptyLayout.visibility= View.VISIBLE
                                binding.topStrip.visibility= View.GONE
                                binding.bottomStrip.visibility= View.GONE
                            }
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


    fun downfile(urll:String,fileName:String){







//        if(!isFilePresent(fileName)) {
//            var mFile2: File? = File(Environment.getExternalStorageDirectory(), "WallpapersBillionaire")
//            System.out.println("File Foond " + mFile2!!.absolutePath)
//            var mFile3: File? = File(Environment.getExternalStorageDirectory(), "WallpapersBillionaire")
//
//            var downloadId = PRDownloader.download(urll, mFile2!!.absolutePath, fileName)
//                .build()
//                .setOnStartOrResumeListener(object : OnStartOrResumeListener {
//                    override fun onStartOrResume() {
//                        System.out.println("??????????????????? start")
//                    }
//                })
//                .setOnPauseListener(object : OnPauseListener {
//                    override fun onPause() {
//                    }
//                })
//                .setOnCancelListener(object : OnCancelListener {
//                    override fun onCancel() {
//                    }
//                })
//                .setOnProgressListener(object : OnProgressListener {
//                    override fun onProgress(progress: Progress) {
//                     //   circlePeView.visibility = View.VISIBLE
//
//                        var per = (progress.currentBytes.toFloat() / progress.totalBytes.toFloat()) * 100.00
//                        //var perint = per*100
//                        System.out.println("::??????????????????? Per : " + per + " ?? : " + progress.currentBytes + " ?? : " + progress.totalBytes)
//
//                        //circlePeView.setProgress(per.toInt())
//                    }
//                })
//                .start(object : OnDownloadListener {
//                    override fun onDownloadComplete() {
//
////                        circlePeView.visibility = View.GONE
////                        circlePeView.setProgress(0)
////                        prefs = getSharedPreferences(PREFS_FILENAME, 0)
//
//                        val editor = prefs!!.edit()
//                        editor.putString(wall, "WallpapersBillionaire/" + fileName)
//                        editor.apply()
//
//                        try {
//                            val myWallpaperManager = WallpaperManager.getInstance(applicationContext)
//                            try {
//                                myWallpaperManager.setResource(R.raw.wallp)
//                            } catch (e: IOException) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace()
//                            }
//
//                            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
//                                ComponentName(this@LadgerListingActivity, VideoLiveWallpaperService::class.java)
//                            )
//                            startActivity(intent)
//                        } catch (e: Exception) {
//                            val intent = Intent()
//                            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
//                            try {
//                                startActivity(intent)
//                            }catch (e2: java.lang.Exception){
//                                Toast.makeText(applicationContext,"Please long click on your home screen. Select Video Live Wallpapers form thier. Thanks",Toast.LENGTH_LONG).show()
//                            }
//
//                        }
//                        System.out.println("??????????????????? complete")
//                    }
//
//                    override fun onError(error: Error) {
//                        System.out.println("??????????????????? error " + error)
//                    }
//                })
//            System.out.println("??????????????????? called")
//        }else{
//            System.out.println("File Foond ")
//            circlePeView.visibility = View.GONE
//            circlePeView.setProgress(0)
//            prefs = getSharedPreferences(PREFS_FILENAME, 0)
//
//            val editor = prefs!!.edit()
//            editor.putString(wall, "WallpapersBillionaire/" + fileName)
//            editor.apply()
//
//            try {
//                val myWallpaperManager = WallpaperManager.getInstance(applicationContext)
//                try {
//                    myWallpaperManager.setResource(R.raw.wallp)
//                } catch (e: IOException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//                val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
//                    ComponentName(this@LadgerListingActivity, VideoLiveWallpaperService::class.java))
//                startActivity(intent)
//            } catch (e: Exception) {
//                val intent = Intent()
//                intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
//
//                try {
//                    startActivity(intent)
//                }catch (e2: java.lang.Exception){
//                    Toast.makeText(applicationContext,"Please long click on your home screen. Select Video Live Wallpapers form thier. Thanks",Toast.LENGTH_LONG).show()
//                }
//
//            }
//            System.out.println("??????????????????? complete")
//        }
    }



}
