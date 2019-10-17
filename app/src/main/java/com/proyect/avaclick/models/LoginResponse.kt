package com.proyect.avaclick.models

data class LoginResponse(val success: Boolean,val exist: Boolean,  val user: User = User(), val session:String)




