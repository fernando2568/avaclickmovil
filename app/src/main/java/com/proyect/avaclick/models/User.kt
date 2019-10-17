package com.proyect.avaclick.models

import com.proyect.avaclick.entity.orm.IColumn

data class User(  @IColumn(name = "ID", dataType = "int")
                  val IdUsuario: Int = 0,

                  @IColumn(name = "Codigo", dataType = "text")
                  val Codigo: String = "",

                  @IColumn(name = "idPersona", dataType = "int")
                  val idPersona: Int = 0,

                  @IColumn(name = "DomainUser", dataType = "int")
                  val DomainUser: String = "",

                  @IColumn(name = "IsAdministrator", dataType = "text")
                  val IsAdministrator: Boolean = false,

                  @IColumn(name = "Password", dataType = "text")
                  val Password: String = "",

                  @IColumn(name = "Correo", dataType = "text")
                  val Correo: String = "",

                  @IColumn(name = "IdRol", dataType = "int")
                  val IdRol: Int = 0,

                  @IColumn(name = "Activo", dataType = "text")
                  val Activo: Boolean = false,

                  @IColumn(name = "IdSucursal", dataType = "int")
                  val IdSucursal: Int = 0,

                  @IColumn(name = "IdEmpresa", dataType = "int")
                  val IdEmpresa: Int = 0,

                  val Persona: Persona = Persona() )