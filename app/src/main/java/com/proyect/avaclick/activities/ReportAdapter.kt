package com.proyect.avaclick.activities

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.Reporte
import kotlinx.android.synthetic.main.report_list_item.view.*
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

        rowView.show_pdf.setOnClickListener {
            val folder_main = "/Reportes/"
            val file = File(Environment.getExternalStorageDirectory(), folder_main + report.Folio + ".pdf")
            if(file.exists()){
                if(file.length() <= 21510){
                    file.delete()
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Atención!!")
                    builder.setMessage("Reporte no disponible.")
                    builder.setIcon(R.drawable.alert)
                    builder.setPositiveButton("Aceptar"){dialog, which ->
                        dialog.cancel()
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }else{
                    val intent = Intent(context, PdfActivity::class.java)
                    intent.putExtra("pdfFile", report.Folio + ".pdf")
                    context.startActivity(intent)
                }
            }else{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Atención!!")
                builder.setMessage("Reporte no disponible.")
                builder.setIcon(R.drawable.alert)
                builder.setPositiveButton("Aceptar"){dialog, which ->
                    dialog.cancel()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
        rowView.share_report.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val folder_main = "/Reportes/"
            val file = File(Environment.getExternalStorageDirectory(), folder_main + report.Folio + ".pdf")
            //val url = "https://avaclick.com" + report.UrlPdf
            val url = "http://192.168.15.100:8081" + report.UrlPdf
            if(file.exists()){
                if(file.length() <= 21510){
                    file.delete()
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Atención!!")
                    builder.setMessage("Reporte no disponible.")
                    builder.setIcon(R.drawable.alert)
                    builder.setPositiveButton("Aceptar"){dialog, which ->
                        dialog.cancel()
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }else{
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartir Reporte")
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    shareIntent.setType("text/plain")
                    shareIntent.putExtra(Intent.EXTRA_TEXT, url)
                    context.startActivity(Intent.createChooser(shareIntent, "AVACLICK"))
                }
            }else{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Atención!!")
                builder.setMessage("Reporte no disponible.")
                builder.setIcon(R.drawable.alert)
                builder.setPositiveButton("Aceptar"){dialog, which ->
                    dialog.cancel()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        return rowView
    }
}