package com.accountapp.accounts.utils

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.IOException

class DownloadFile(pDialog: ProgressDialog?, mContext: Context?) : AsyncTask<String, Void, Void>() {

    var pDialog: ProgressDialog
    var context: Context

    init {
        this.pDialog = pDialog!!
        this.context = mContext!!
    }

    override fun onPreExecute() {
        super.onPreExecute()
        showpDialog(pDialog)
    }

    override fun doInBackground(vararg strings: String): Void? {
        val fileUrl = strings[0]   // -> http://maven.apache.org/maven-1.x/maven.pdf
        val fileName = strings[1]  // -> maven.pdf
        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
        val folder = File(extStorageDirectory, "Account")
        folder.mkdir()

        val pdfFile = File(folder, fileName)

        try {
            pdfFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        FileDownloader.downloadFile(fileUrl, pdfFile)
        return null
    }

    override fun onPostExecute(aVoid: Void) {
        super.onPostExecute(aVoid)
        this.pDialog=pDialog
        hidepDialog(pDialog)

        Toast.makeText(context, "Download PDf successfully", Toast.LENGTH_SHORT)
            .show()

        Log.d("Download complete", "----------")
    }


    private fun showpDialog(pDialog: ProgressDialog) {
        if (!pDialog!!.isShowing())
            pDialog!!.show()
    }

    private fun hidepDialog(pDialog: ProgressDialog) {
        if (pDialog!!.isShowing())
            pDialog!!.dismiss()
    }

}
