package com.proyect.avaclick.api

import android.icu.text.DecimalFormat
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

    @FormUrlEncoded
    @POST("Registro")
    fun registryUser(
        @Field("mailUser") email:String,
        @Field("passwordUser") password: String,
        @Field("nameUser") name: String,
        @Field("lastNameUser") lastNameUser: String,
        @Field("lastSecondNameUser") lastSecondNameUser: String,
        @Field("telHomeUser") telephone: String,
        @Field("telOfficeUser") officeTelephone: String,
        @Field("celUser") cellphone:String
    ):Call<DefaultResponse>
}