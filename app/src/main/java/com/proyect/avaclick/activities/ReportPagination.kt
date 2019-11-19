package com.proyect.avaclick.activities

import com.proyect.avaclick.models.Reporte

class ReportPagination(_totalItems: Int, _itemsPerPage: Int, _itemsRemaining: Int, _lastPage: Int, _reports: ArrayList<Reporte>){

    var totalItems: Int
    var itemsPerPage: Int
    var itemsRemaining: Int
    var lastPage: Int
    val reports: ArrayList<Reporte>

    init{
        this.totalItems = _totalItems
        this.itemsPerPage = _itemsPerPage
        this.itemsRemaining = _itemsRemaining
        this.lastPage = _lastPage
        this.reports = _reports
    }

    fun generatePage(currentPage: Int): ArrayList<Reporte>{

        var startItem = currentPage * this.itemsPerPage
        var numOfData = this.itemsPerPage
        val pageData: ArrayList<Reporte> = ArrayList()

        if(currentPage==this.lastPage && this.itemsRemaining > 0){
            for (i in startItem .. (startItem + (this.itemsPerPage-1))) {
                val report: Reporte = reports[i]
                val itemOfPage = Reporte(
                    IdAlmacen = report.IdAlmacen,
                    Domicilio = report.Domicilio,
                    Fecha = report.Fecha,
                    Folio =  report.Folio,
                    ValorInvestigado = report.ValorInvestigado,
                    ValorAObtener = report.ValorAObtener,
                    UrlPdf = report.UrlPdf
                )
                pageData.add(itemOfPage)
            }
        }else{
            for(i in startItem .. (startItem + (numOfData-1))){
                val report: Reporte = reports[i]
                val itemOfPage = Reporte(
                    IdAlmacen = report.IdAlmacen,
                    Domicilio = report.Domicilio,
                    Fecha = report.Fecha,
                    Folio =  report.Folio,
                    ValorInvestigado = report.ValorInvestigado,
                    ValorAObtener = report.ValorAObtener,
                    UrlPdf = report.UrlPdf
                )
                pageData.add(itemOfPage)
            }
        }
        return pageData
    }
}