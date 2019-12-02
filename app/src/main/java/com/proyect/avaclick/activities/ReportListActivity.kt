package com.proyect.avaclick.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
import kotlin.math.absoluteValue

class ReportListActivity : AppCompatActivity() {
    val context = this
    var currentPage = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_list)
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
                        /*rvReportList.layoutManager = LinearLayoutManager(context)
                        rvReportList.layoutManager = GridferLayoutManager(context, 1)
                        rvReportList.adapter = ReportAdapter(reports, context)*/
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
}
