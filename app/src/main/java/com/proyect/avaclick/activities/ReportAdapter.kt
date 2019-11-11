package com.proyect.avaclick.activities

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.proyect.avaclick.R
import kotlinx.android.synthetic.main.report_list_item.view.*
import com.proyect.avaclick.models.Reporte

class ReportAdapter(val items : ArrayList<Reporte>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.report_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report: Reporte = items[position]
        holder?.reporte_folio?.text = report.Folio
        holder?.reporte_domicilio?.text = report.Domicilio
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val reporte_folio = view.reporte_folio
    val reporte_domicilio = view.reporte_domicilio
}