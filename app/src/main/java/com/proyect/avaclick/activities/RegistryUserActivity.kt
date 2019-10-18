package com.proyect.avaclick.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registry_user.*
import android.widget.Toast
import com.proyect.avaclick.R

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
        var usuario = editTextUsuario.text.toString()
        var apellidos = editTextApellidos.text.toString()
        var password = editTextPassword.text.toString()
        var rpassword = editTextRepPassword.text.toString()
        var correo = editTextCorreo.text.toString()

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
            Toast.makeText(this, encodedPassword, Toast.LENGTH_LONG).show()
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