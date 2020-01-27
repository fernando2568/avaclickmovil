package com.proyect.avaclick.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.proyect.avaclick.R
import kotlinx.android.synthetic.main.activity_perfil_usuario.*

class PerfilUserActivity : AppCompatActivity(){
    val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        var button = buttonEditar
        var buttonBack = back_btn
        var textBack = toolbar_back
        buttonBack?.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        textBack?.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        button?.setOnClickListener {
            var usuario   = txtNombre.text.toString()
            var apellidos = txtApellidos.text.toString()
            var password  = txtPassword.text.toString()
            var rpassword = txtPasswordRep.text.toString()
            var telefono  = txtTelcasa.text.toString()
            var celular   = txtTelCel.text.toString()
            var oficina   = txtTelOficina.text.toString()

            if(password != ""){
                if(password.length < 6){
                    val builder = AlertDialog.Builder(this@PerfilUserActivity)
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
                }else if(password != rpassword){
                    val builder = AlertDialog.Builder(this@PerfilUserActivity)
                    builder.setTitle("Atención!!")
                    builder.setMessage("Las contraseñas no coinciden.")
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
            }
        }

        txtPassword.customSelectionActionModeCallback = object : ActionMode.Callback {

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
        txtPassword.isLongClickable = false

        txtPasswordRep.customSelectionActionModeCallback = object : ActionMode.Callback {

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
        txtPasswordRep.isLongClickable = false

        show_pass_btn.setOnClickListener{
            if(txtPasswordRep.transformationMethod == PasswordTransformationMethod.getInstance()){
                show_pass_btn.setImageResource(R.drawable.eye_hide)
                txtPasswordRep.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                show_pass_btn.setImageResource(R.drawable.eye)
                txtPasswordRep.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
}