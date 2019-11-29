package com.proyect.avaclick.activities

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.proyect.avaclick.R
import kotlinx.android.synthetic.main.report_list_item.view.*
import com.proyect.avaclick.models.Reporte

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

        return rowView
    }
}