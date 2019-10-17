package com.proyect.avaclick.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.proyect.avaclick.models.DefaultResponse
import com.proyect.avaclick.models.LoginResponse

interface Api {


    @FormUrlEncoded
    @POST("LogIn")
    fun userLogin(
            @Field("user") email:String,
            @Field("pass") password: String
    ):Call<LoginResponse>
}