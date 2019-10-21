package com.proyect.avaclick.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registry_user.*
import android.widget.Toast
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.DefaultResponse
import com.proyect.avaclick.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistryUserActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry_user)

        var button = buttonAgregar

        button?.setOnClickListener {
            validateUserOnCreate()
        }
    }

    fun validateUserOnCreate(){
        var usuario   = editTextUsuario.text.toString()
        var apellidos = editTextApellidos.text.toString()
        var password  = editTextPassword.text.toString()
        var rpassword = editTextRepPassword.text.toString()
        var correo    = editTextCorreo.text.toString()

        if(usuario == ""){
            Toast.makeText(this, "Debes ingresar un nombre.", Toast.LENGTH_LONG).show()
        }else if(apellidos == ""){
            Toast.makeText(this, "Debes ingresar apellidos.", Toast.LENGTH_LONG).show()
        }else if(password != rpassword){
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show()
        }else if(password.length < 6){
            Toast.makeText(this, "La contraseña debe tener minimo 6 caracteres.", Toast.LENGTH_LONG).show()
        }else if(correo == ""){
            Toast.makeText(this, "Debes ingrear un correo.", Toast.LENGTH_LONG).show()
        }else if(!(isEmailValid(correo))){
            Toast.makeText(this, "El correo no es valido.", Toast.LENGTH_LONG).show()
        }else if(!(checkBoxTerminos.isChecked)){
            Toast.makeText(this, "Tiene que aceptar terminos y condiciones.", Toast.LENGTH_LONG).show()
        }
        else{
            //Crar usuario nuevo
            var encodedPassword = encode16(password)
            RetrofitClient.instance.registryUser(correo,password,usuario,usuario,usuario,"33175689", "33174589", "3323564578")
                .enqueue(object: Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if(response.body()?.valido === true){

                            Toast.makeText(applicationContext,response.toString(), Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)


                        }else{
                            Toast.makeText(applicationContext, response.body()?.valido.toString(), Toast.LENGTH_LONG).show()
                        }

                    }
                })
        }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun encode16(password: String): String {
        var charset = Charsets.UTF_8
        var byteArray = password.toByteArray(charset)
        var encoded = ""
        for (b in byteArray) {
            val st = String.format("%02X", b)
            encoded = encoded.plus(st)
        }
        return encoded
    }
}