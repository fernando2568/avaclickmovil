package com.proyect.avaclick.activities

import android.os.AsyncTask
import java.net.URL

class DownloadFile() : AsyncTask<String, String, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg _url: String?): String {
        var count: Int
        try {
            val url = URL(_url[0])
            val connection = url.openConnection()
        }
        catch (e: Exception) {
        }
        var test="test"
        return test
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

}