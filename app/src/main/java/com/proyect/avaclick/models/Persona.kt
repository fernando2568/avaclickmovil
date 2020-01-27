package com.proyect.avaclick.models

import com.proyect.avaclick.entity.orm.IColumn
import com.proyect.avaclick.entity.orm.IEntity

@IEntity(table = "Persona", primaryKey = ["IdPersona"])
//Para crear la tabla en la base de datos
data class Persona(   @IColumn(name = "IdPersona", dataType = "text")
                      val IdPersona: Int = 0,

                      @IColumn(name = "Codigo", dataType = "text")
                      val Codigo: String = "",

                      @IColumn(name = "Nombre", dataType = "text")
                      val Nombre: String = "",

                      @IColumn(name = "ApellidoPat", dataType = "text")
                      val ApellidoPat: String = "",

                      @IColumn(name = "ApellidoMat", dataType = "text")
                      val ApellidoMat: String = "",

                      @IColumn(name = "Correo", dataType = "text")
                      val Correo: String = "",

                      @IColumn(name = "TelCasa", dataType = "text")
                      val TelCasa: String = "",

                      @IColumn(name = "TelOficina", dataType = "text")
                      val TelOficina: String = "",

                      @IColumn(name = "Celular", dataType = "text")
                      val Celular: String = "",

                      @IColumn(name = "Activo", dataType = "text")
                      val Activo: Boolean = false,

                      @IColumn(name = "IdSucursal", dataType = "text")
                      val IdSucursal: Int = 0,

                      @IColumn(name = "IdEmpresa", dataType = "text")
                      val IdEmpresa: Int = 0)