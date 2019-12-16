package com.proyect.avaclick.activities

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import kotlinx.android.synthetic.main.report_list_item.view.*
import com.proyect.avaclick.models.Reporte
import java.io.File

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
            }else{
                Toast.makeText(context, "Archivo ya descargado", Toast.LENGTH_LONG).show()
            }
        }

        return rowView
    }
}