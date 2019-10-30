package com.proyect.avaclick.activities

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_registry_user.*
import android.widget.Toast
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.DefaultResponse
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View.OnTouchListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.*


class RegistryUserActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry_user)

        var button = buttonAgregar
        toolbar.setNavigationIcon(R.drawable.arrowleft_white)
        toolbar.setNavigationOnClickListener{
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        button?.setOnClickListener {
            if(button.backgroundTintList == ColorStateList.valueOf(resources.getColor(R.color.grey_600))){
                Toast.makeText(this, "Tiene que aceptar terminos y condiciones.", Toast.LENGTH_LONG).show()
            }else{
                validateUserOnCreate()
            }
        }

        checkBoxTerminos.setOnCheckedChangeListener { buttonView, isChecked ->
            if(!checkBoxTerminos.isChecked){
                button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey_600))
            }else{
                button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_500))
            }
        }

        editTextPassword.customSelectionActionModeCallback = object : ActionMode.Callback {

            override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
                return false
            }

            override fun onDestroyActionMode(actionMode: ActionMode) {}
        }
        editTextPassword.isLongClickable = false

        editTextRepPassword.customSelectionActionModeCallback = object : ActionMode.Callback {

            override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
                return false
            }

            override fun onDestroyActionMode(actionMode: ActionMode) {}
        }
        editTextRepPassword.isLongClickable = false

        show_pass_btn.setOnClickListener{
            if(editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance()){
                show_pass_btn.setImageResource(R.drawable.eye_hide)
                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                show_pass_btn.setImageResource(R.drawable.eye)
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    fun validateUserOnCreate(){
        var usuario   = editTextUsuario.text.toString()
        var apellidos = editTextApellidos.text.toString()
        var password  = editTextPassword.text.toString()
        var rpassword = editTextRepPassword.text.toString()
        var correo    = editTextCorreo.text.toString()
        var telefono    = editTextTelefono.text.toString()

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
        }else if(telefono == ""){
            Toast.makeText(this, "Debes ingrear un numero de telefono.", Toast.LENGTH_LONG).show()
        }else if(!(checkBoxTerminos.isChecked)){
            Toast.makeText(this, "Tiene que aceptar terminos y condiciones.", Toast.LENGTH_LONG).show()
        }
        else{
            //Crar usuario nuevo
            var encodedPassword = encode16(password)
            var arrayApellidos: List<String> = apellidos.split(" ")
            var apPaterno = arrayApellidos[0]
            var apMaterno = arrayApellidos[1]

            RetrofitClient.instance.registryUser(correo,encodedPassword,usuario,apPaterno,apMaterno,telefono, "", "")
                .enqueue(object: Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) =
                        if(response.body()?.valido?.equals(true) ?: (true === null)){
                            Toast.makeText(applicationContext,response.toString(), Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext, response.body()?.valido.toString(), Toast.LENGTH_LONG).show()
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