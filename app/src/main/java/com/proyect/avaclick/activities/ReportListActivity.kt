package com.proyect.avaclick.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.ListReportResponse
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.ContextCompat
import com.proyect.avaclick.models.Reporte
import com.proyect.avaclick.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_report_list.*
import java.io.File
import android.os.Environment
import androidx.core.app.ActivityCompat

class ReportListActivity : AppCompatActivity() {
    val context = this
    var currentPage = 0
    var MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_list)
        //
        val reports: ArrayList<Reporte> = ArrayList()
        back_btn?.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        toolbar_back?.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        RetrofitClient.instance.listReports(SharedPrefManager.getInstance(this).loggedUser)
            .enqueue(object: Callback<ListReportResponse> {
                override fun onFailure(call: Call<ListReportResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ListReportResponse>, response: Response<ListReportResponse>) =
                    if(response.body()?.success?.equals(true) ?: (true === null)){
                        //Carga de datos
                        response.body()?.listado?.forEach(){
                            val report = Reporte(
                                IdAlmacen = it.IdAlmacen,
                                Domicilio = it.Domicilio,
                                Fecha = it.Fecha,
                                Folio =  it.Folio,
                                ValorInvestigado = it.ValorInvestigado,
                                ValorAObtener = it.ValorAObtener,
                                UrlPdf = it.UrlPdf
                            )
                            reports.add(report)
                        }

                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                val builder = AlertDialog.Builder(this@ReportListActivity)
                                builder.setTitle("Atención!!")
                                builder.setMessage("Se debe aceptar los permisos correspondientes para acceder a este listado.")
                                builder.setIcon(R.drawable.alert)
                                builder.setPositiveButton("Aceptar"){dialog, which ->
                                    val intent = Intent(applicationContext, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                                val dialog: AlertDialog = builder.create()
                                dialog.show()
                            } else {
                                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                            }
                        } else {
                            val folder_main = "Reportes"

                            val folder = File(Environment.getExternalStorageDirectory(), folder_main)
                            if(!folder.exists()){
                                folder.mkdirs()
                            }

                            var reportPagination = ReportPagination(reports.size,10, reports.size % 10, reports.size/10, reports)
                            var totalPages: Int = reportPagination.totalItems / reportPagination.itemsPerPage

                            previo.isEnabled = false
                            //Adapter
                            val adapter = ReportAdapter(context, reportPagination.generatePage(currentPage))
                            listReportes.adapter = adapter

                            siguiente.setOnClickListener {
                                currentPage = currentPage?.plus(1)
                                val adapter = ReportAdapter(context, reportPagination.generatePage(currentPage))
                                listReportes.adapter = adapter
                                toggleButtons(currentPage, totalPages)
                            }

                            previo.setOnClickListener {
                                currentPage = currentPage?.dec()
                                val adapter = ReportAdapter(context, reportPagination.generatePage(currentPage))
                                listReportes.adapter = adapter
                                toggleButtons(currentPage, totalPages)
                            }
                        }
                    }else{
                        val builder = AlertDialog.Builder(this@ReportListActivity)
                        builder.setTitle("Error!!")
                        builder.setMessage(response.body()?.success.toString())
                        builder.setIcon(R.drawable.alert)
                        builder.setPositiveButton("Aceptar"){dialog, which ->
                            dialog.cancel()
                        }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()

                        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setTextColor(resources.getColor(R.color.white))
                        positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
                        positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
                    }
            })
    }

    fun toggleButtons(currentPage: Int, totalPages: Int){
       if(currentPage == totalPages){
           siguiente.isEnabled = false
           siguiente.setBackgroundResource(R.drawable.round_button_list_disabled)
           previo.isEnabled = true
           previo.setBackgroundResource(R.drawable.round_button_list)
       }else if(currentPage == 0){
           previo.isEnabled = false
           previo.setBackgroundResource(R.drawable.round_button_list_disabled)
           siguiente.isEnabled = true
           siguiente.setBackgroundResource(R.drawable.round_button_list)
       }else if(currentPage >= 1){
           previo.isEnabled =  true
           previo.setBackgroundResource(R.drawable.round_button_list)
           siguiente.isEnabled = true
           siguiente.setBackgroundResource(R.drawable.round_button_list)
       }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    finish()
                    startActivity(getIntent())
                } else {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                }
                return
            }
            else -> {
            }
        }
    }
}
