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
import androidx.core.content.ContextCompat


class RegistryUserActivity : AppCompatActivity(){
    val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry_user)

        var button = buttonAgregar
        var buttonBack = back_btn
        var textBack = toolbar_back
        buttonBack?.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        textBack?.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        button?.setOnClickListener {
            if(button.backgroundTintList == ColorStateList.valueOf(resources.getColor(R.color.grey_600))){
                val builder = AlertDialog.Builder(this@RegistryUserActivity)
                builder.setTitle("Atención!!")
                builder.setMessage("Tiene que aceptar terminos y condiciones.")
                builder.setIcon(R.drawable.alert)
                builder.setPositiveButton("Aceptar"){dialog, which ->
                    dialog.cancel()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()

                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setTextColor(resources.getColor(R.color.white))
                positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
                positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
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
        var telefono  = editTextTelefono.text.toString()
        var celular   = editTextCelular.text.toString()
        var oficina   = editTextTelOficina.text.toString()

        if(usuario == ""){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Debes ingresar un nombre.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(apellidos == ""){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Debes ingresar apellidos.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(password != rpassword){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Las contraseñas no coinciden..")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(password.length < 6){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("La contraseña debe tener minimo 6 caracteres.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(correo == ""){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Debes ingrear un correo.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(!(isEmailValid(correo))){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("El correo no es valido.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(telefono == ""){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Debes ingrear un numero de telefono.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(celular == ""){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Debes ingrear un numero de celular.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(oficina == ""){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Debes ingrear un numero de oficina.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }else if(!(checkBoxTerminos.isChecked)){
            val builder = AlertDialog.Builder(this@RegistryUserActivity)
            builder.setTitle("Atención!!")
            builder.setMessage("Tiene que aceptar terminos y condiciones.")
            builder.setIcon(R.drawable.alert)
            builder.setPositiveButton("Aceptar"){dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.white))
            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
        }
        else{
            //Crar usuario nuevo
            var encodedPassword = encode16(password)
            var arrayApellidos: List<String> = apellidos.split(" ")
            var apPaterno = arrayApellidos[0]
            var apMaterno = arrayApellidos[1]

            RetrofitClient.instance.registryUser(correo,encodedPassword,usuario,apPaterno,apMaterno,telefono, oficina, celular)
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
                            val builder = AlertDialog.Builder(this@RegistryUserActivity)
                            builder.setTitle("Error!!")
                            builder.setMessage(response.body()?.valido.toString())
                            builder.setIcon(R.drawable.alert)
                            builder.setPositiveButton("Aceptar"){dialog, which ->
                                dialog.cancel()
                            }
                            val dialog: AlertDialog = builder.create()
                            dialog.show()

                            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            positiveButton.setTextColor(resources.getColor(R.color.white))
                            positiveButton.setBackgroundColor(resources.getColor(R.color.blue_500))
                            positiveButton.background = ContextCompat.getDrawable(context, R.drawable.round_button_basic)
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