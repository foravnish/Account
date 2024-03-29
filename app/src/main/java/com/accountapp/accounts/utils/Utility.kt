package com.accountapp.accounts.utils

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Environment
import android.text.TextUtils
import android.transition.Explode
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.accountapp.accounts.R
import com.accountapp.accounts.model.response.FYModel
import com.accountapp.accounts.ui.home.HomeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object {

        var isLadger:Boolean = true
        //        private var mediaFactory: MediaFactory? = null
        var dateM: Date? = null
//    fun setConnectivity(context: Context) {
//        try {
//            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val networkInfo = connMgr.activeNetworkInfo
//            val isConnectedToInternet = networkInfo != null && networkInfo.isConnected
//            ApplicationClass.setIsConnected(isConnectedToInternet)
//        } catch (e: Exception) {
//        }
//    }

        fun getCurrentDate(format: String): String {
//        val sdf = SimpleDateFormat("EEE, dd MMMM")
            val sdf = SimpleDateFormat(format)
            val currentDate = sdf.format(Date())
            return currentDate
        }

        fun convertDateFormat(oldFormat: String?, newFormat: String?, dateString: String?): String? {
            var sdf = SimpleDateFormat(oldFormat)
            try {
                val date: Date = sdf.parse(dateString)
                sdf = SimpleDateFormat(newFormat, Locale.US)
                return sdf.format(date)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return ""
        }


        @JvmStatic
        fun getLastFiveFinancialYear(): ArrayList<FYModel> {
            val mList = ArrayList<FYModel>()
            for (i in 0..4) {
                val mMonth = getCurrentDate("yyyy").toInt()
                var fy = ""
                if (i == 0)
                    fy = "FY " + mMonth + "-${mMonth + 1}"
                else
                    fy = "FY " + (mMonth - i) + "-${(mMonth - i) + 1}"
                mList.add(
                    FYModel(
                        fy, "01-APR-${
                            convertDateFormat(
                                "yy", "yyyy", (mMonth - i).toString()
                            )
                        }", "31-MAR-${
                            convertDateFormat(
                                "yy", "yyyy", ((mMonth - i) + 1).toString()
                            )
                        }", false
                    )
                )
            }
            return mList
        }


        fun closeKeyboard(view: View, context: Context) {
            if (view != null) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }


        fun startActivity(context: Context, intent: Intent) {
            context.startActivity(intent)
        }

        fun startActivityFromExplode(context: Activity, intent: Intent) {

            val explode = Explode()
            explode.duration = 500
            context.window.exitTransition = explode
            context.window.enterTransition = explode
            val oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(context)
            val i2 = Intent(context, HomeActivity::class.java)
            context.startActivity(i2, oc2.toBundle())

        }


        fun startActivityWithLeftToRightAnimation(ctx: Activity?, `in`: Intent?) {
            if (ctx != null && `in` != null) {
                ctx.startActivity(`in`)
                ctx.overridePendingTransition(R.anim.slide_in_right, R.anim.scale_down)
            }
        }


        fun startActivityBottomToUpAnimation(ctx: Activity?, `in`: Intent?) {
            if (ctx != null && `in` != null) {
                ctx.startActivity(`in`)
                ctx.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            }
        }

        fun startActivityUpToBottomAnimation(ctx: Activity?, `in`: Intent?) {
            if (ctx != null && `in` != null) {
                ctx.startActivity(`in`)
                ctx.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
            }
        }


        fun showToast(mContext: Context, message: String) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }


        fun setImageViaGlide(placeholderId: Int, imageUrl: String, imgVw: ImageView, mContext: Context) {
            if (!TextUtils.isEmpty(imageUrl) && imgVw!= null) {
                val requestOptions: RequestOptions =
                    RequestOptions().placeholder(placeholderId).error(placeholderId).fallback(placeholderId)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                Glide.with(mContext).load(imageUrl).apply(requestOptions).into(imgVw)
            }
        }


        fun showSnackBar(parentLayout: View?, msg: String) {
            if (parentLayout != null) {
                val snackBar = Snackbar.make(parentLayout, msg, 5000)
                snackBar.setActionTextColor(Color.WHITE)

                val view = snackBar.view
                val tv = view.findViewById(R.id.snackbar_text) as TextView
                tv.setTextColor(Color.WHITE)
                //view.setBackgroundColor(parentLayout.context.resources.getColor(R.color.blue_03A9F4))
                snackBar.show()
            }
        }



        fun toTitleCase(str: String?): String? {

            if (str == null) {
                return null
            }

            var space = true
            val builder = StringBuilder(str)
            val len = builder.length

            for (i in 0 until len) {
                val c = builder[i]
                if (space) {
                    if (!Character.isWhitespace(c)) {
                        // Convert to title case and switch out of whitespace mode.
                        builder.setCharAt(i, Character.toTitleCase(c))
                        space = false
                    }
                } else if (Character.isWhitespace(c)) {
                    space = true
                } else {
                    builder.setCharAt(i, Character.toLowerCase(c))
                }
            }

            return builder.toString()
        }




//        fun getLayoutManagerAsPerDevice(mContext: Context): GridLayoutManager {
//            var layoutManager: RecyclerView.LayoutManager
//            //check if we are running app on tablet or on mobile phone
//            val isTablet = mContext.resources.getBoolean(R.bool.isTablet)
//            if (isTablet)
//                layoutManager = GridLayoutManager(mContext, 4)
//            else
//                layoutManager = GridLayoutManager(mContext, 2)
//            return layoutManager
//        }




        //method to convert server utc to local time
        fun getNewDate(millis: String, ctx: Context): String {

            val time = java.lang.Long.valueOf(millis)
            val calendar = Calendar.getInstance()
            val tz = TimeZone.getDefault()
            calendar.timeInMillis = time * 1000
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val currenTimeZone = calendar.time as Date
            val month = currenTimeZone.month + 1
            val year = currenTimeZone.year + 1900
            return currenTimeZone.date.toString() + " " + convertMonthNumaricToAlpha(month) + " " + year

//            return  DateUtils.formatDateTime(ctx, millis.toLong() * 1000, DateUtils.FORMAT_SHOW_YEAR)
        }

        private fun convertMonthNumaricToAlpha(month: Int): Any? {
            var months: String? = null
            when (month) {
                1 -> months = "Jan"
                2 -> months = "Feb"
                3 -> months = "Mar"
                4 -> months = "Apr"
                5 -> months = "May"
                6 -> months = "Jun"
                7 -> months = "Jul"
                8 -> months = "Aug"
                9 -> months = "Sep"
                10 -> months = "Oct"
                11 -> months = "Nov"
                12 -> months = "Dec"
            }
            return months
        }


        private fun convertMonthNumaricToAlphaFull(month: Int): Any? {
            var months: String? = null
            when (month) {
                1 -> months = "January"
                2 -> months = "February"
                3 -> months = "March"
                4 -> months = "April"
                5 -> months = "May"
                6 -> months = "June"
                7 -> months = "July"
                8 -> months = "August"
                9 -> months = "September"
                10 -> months = "October"
                11 -> months = "November"
                12 -> months = "December"
            }
            return months
        }




        //method to convert server utc to local time
        fun getMongoDate(millis: String): String {

            val date: Date?
            var  strDate=""
            try {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val dateString=millis
                date = format.parse(""+dateString)
                val day=date.date
                val months=date.month+1
                val year=date.year+1900
                strDate= ""+day+"-"+months+"-"+year
            } catch (e: Exception) {
            }
            return strDate
        }

        fun getMongoDateMonth(millis: String): String {

            val date: Date?
            var  strDate=""
            try {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val dateString=millis
                date = format.parse(""+dateString)
                val day=date.date
                val months=date.month+1
                val year=date.year+1900
                strDate= ""+convertMonthNumaricToAlpha(months)+" "+day+", "+year
            } catch (e: Exception) {
            }
            return strDate
        }



        @JvmStatic
        fun showPgDialog(ctx: Context?): Dialog? {
            if (ctx != null) {
                val dialog = Dialog(ctx)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                //            dialog.setCancelable(false);

                dialog.setContentView(R.layout.dialogprogress)
                dialog.setCanceledOnTouchOutside(false)

                dialog.show()

                return dialog
            }
            return null
        }


        fun getRootDirPath(context: Context): String {
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                val file = ContextCompat.getExternalFilesDirs(
                    context.applicationContext,
                    null
                )[0]
                return file.getAbsolutePath()
            } else {
                return context.applicationContext.filesDir.absolutePath
            }
        }

        fun getProgressDisplayLine(currentBytes: Long, totalBytes: Long): String {
            return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes)
        }

        private fun getBytesToMBString(bytes: Long): String {
            return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00))
        }



        fun openPdfWithIntent(filePath: String,context: Context) {
            val file = File(filePath)
            val context = context
            val pdfViewIntent = Intent(Intent.ACTION_VIEW)

            val apkURI = FileProvider.getUriForFile(
                context,
                context.getApplicationContext()
                    .getPackageName() + ".provider", file
            )

            pdfViewIntent.setDataAndType(apkURI, "application/pdf")
            pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val intent = Intent.createChooser(pdfViewIntent, "Open Account Pdf")
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(context,"No suitable App Found to view pdf",Toast.LENGTH_LONG).show()
            }

        }

    }

}