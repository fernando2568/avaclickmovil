package com.proyect.avaclick.models

import com.proyect.avaclick.entity.orm.IColumn
import com.proyect.avaclick.entity.orm.IEntity

@IEntity(table = "Reporte", primaryKey = ["IdAlmacen"])
data class Reporte (@IColumn(name = "IdAlmacen", dataType = "text")
                    val IdAlmacen: String = "",

                    @IColumn(name = "Domicilio", dataType = "text")
                    val Domicilio: String = "",

                    @IColumn(name = "Fecha", dataType = "text")
                    val Fecha: String = "",

                    @IColumn(name = "Folio", dataType = "text")
                    val Folio: String = "",

                    @IColumn(name = "ValorInvestigado", dataType = "text")
                    val ValorInvestigado: String = "",

                    @IColumn(name = "ValorAObtener", dataType = "text")
                    val ValorAObtener: Boolean = false,

                    @IColumn(name = "UrlPdf", dataType = "text")
                    val UrlPdf: String = "")
