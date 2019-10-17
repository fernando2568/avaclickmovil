package com.proyect.avaclick.models

import com.proyect.avaclick.entity.orm.IColumn
import com.proyect.avaclick.entity.orm.IEntity

@IEntity(table = "Persona", primaryKey = ["ID"])
//Para crear la tabla en la base de datos
data class Persona(   @IColumn(name = "IsAdministrator", dataType = "text")
                      val IdPersona: Int = 0,

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val Codigo: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val Nombre: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val ApellidoPat: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val ApellidoMat: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val Correo: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val TelCasa: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val TelOficina: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val Celular: String = "",

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val Activo: Boolean = false,

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val IdSucursal: Int = 0,

                      @IColumn(name = "IsAdministrator", dataType = "text")
                      val IdEmpresa: Int = 0)