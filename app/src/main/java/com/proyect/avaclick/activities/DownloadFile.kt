package com.proyect.avaclick.activities

import android.os.AsyncTask
import android.os.Environment
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DownloadFile() : AsyncTask<String, String, String>() {

    var fileName: String = ""
    var folder: String = ""

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg _url: String): String? {
        var count: Int
        try {
            val url = URL(_url[0])
            val connection = url.openConnection()
            connection.connect()

            val input: InputStream = BufferedInputStream(url.openStream(), 8192)
            val timestamp: String = SimpleDateFormat("yyyy.MM.dd.mm.ss").format(Date())

            fileName = _url[0].substring(_url[0]?.lastIndexOf('/').plus(1), _url[0].length)
            fileName = timestamp + "_" + fileName

            val folder_main = "Reportes"

            val folder = File(Environment.getExternalStorageDirectory(), folder_main)
            if(!folder.exists()){
                folder.mkdirs()
            }

            val output: OutputStream = FileOutputStream(folder_main + fileName)
            var data = ByteArray(1024)
            var total: Long = 0
            count = input.read(data)
            while (count != -1) {
                total += count
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            return "Downloaded"
        }
        catch (e: Exception) {
            return e.message
        }
        return "Wrong Download"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

}