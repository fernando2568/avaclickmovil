package com.proyect.avaclick.activities

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import kotlinx.android.synthetic.main.report_list_item.view.*
import com.proyect.avaclick.models.Reporte
import java.io.File
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.nio.file.FileSystem

class ReportAdapter(val context: Context, val dataSource: ArrayList<Reporte>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.report_list_item, parent, false)
        val report = getItem(position) as Reporte
        rowView.reporte_folio.text = report.Folio
        rowView.reporte_domicilio.text = report.Domicilio

        val downloadmanager: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(RetrofitClient.BASE_URL_DOWNLOAD + report.UrlPdf)
        rowView.show_pdf.setOnClickListener {
            val folder_main = "/Reportes/"
            val file = File(Environment.getExternalStorageDirectory(), folder_main + report.Folio + ".pdf")
            if(!file.exists()){
                val request: DownloadManager.Request = DownloadManager.Request(uri)
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                request.setAllowedOverRoaming(false).setTitle("Descarga")
                request.setDescription("Descarga de reporte en PDF.")
                request.setDestinationInExternalPublicDir("/Reportes", report.Folio + ".pdf")
                downloadmanager.enqueue(request)
                val intent = Intent(context, PdfActivity::class.java)
                intent.putExtra("pdfFile", report.Folio + ".pdf")
                context.startActivity(intent)
            }else{
                val intent = Intent(context, PdfActivity::class.java)
                intent.putExtra("pdfFile", report.Folio + ".pdf")
                context.startActivity(intent)
            }
        }
        rowView.share_report.setOnClickListener {
            val resultIntent = Intent(Intent.ACTION_SEND)
            val folder_main = "/Reportes/"
            val file = File(Environment.getExternalStorageDirectory(), folder_main + report.Folio + ".pdf")
            if(file.exists()){
                resultIntent.setType("application/pdf")
                resultIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file))
                resultIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartiendo reporte...")
                resultIntent.putExtra(Intent.EXTRA_TEXT, "Compartiendo reporte...")
            }else{
                Toast.makeText(context, "Archivo en ruta especificada, no encontrado", Toast.LENGTH_LONG).show()
            }
            /*val fileUri: Uri? = FileProvider.getUriForFile(context, "com.avaclick.fileprovider", file)
            Toast.makeText(context, fileUri.toString(), Toast.LENGTH_LONG).show()
            if(fileUri != null){
                resultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                resultIntent.setDataAndType(fileUri, "application/pdf")
            }
            (context as Activity).setResult(Activity.RESULT_OK, resultIntent)
            context.finish()*/
        }

        return rowView
    }
}