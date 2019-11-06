package com.proyect.avaclick.activities

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_registry_user.*
import android.widget.Toast
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.ListReportResponse
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View.OnTouchListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.*
import androidx.core.content.ContextCompat
import com.proyect.avaclick.models.session

class ReportListActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_list)

        RetrofitClient.instance.listReports(268)
            .enqueue(object: Callback<ListReportResponse> {
                override fun onFailure(call: Call<ListReportResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ListReportResponse>, response: Response<ListReportResponse>) =
                    if(response.body()?.success?.equals(true) ?: (true === null)){
                        //Carga de datos
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
}
