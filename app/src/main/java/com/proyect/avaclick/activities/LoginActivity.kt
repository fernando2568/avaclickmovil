package com.proyect.avaclick.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TextKeyListener.clear
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.DefaultResponse
import com.proyect.avaclick.models.LoginResponse
import com.proyect.avaclick.storage.SharedPrefManager
import com.proyect.avaclick.util.CustomProgressBar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    val progressBar = CustomProgressBar()
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        show_pass.setOnClickListener{

            if(txtPasswd.transformationMethod == PasswordTransformationMethod.getInstance()){
                show_pass.setBackgroundResource(R.drawable.eye_hide)
                txtPasswd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                show_pass.setBackgroundResource(R.drawable.eye)
                txtPasswd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        lblOlvido.setOnClickListener {
            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.dialog_recuperar,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }

            // Get the widgets reference from custom view
            val correo = view.findViewById<TextView>(R.id.txtUsuario).toString()
            val buttonPopup = view.findViewById<Button>(R.id.btnAceptar)

            // Set click listener for popup window's text view


            // Set a click listener for popup's button widget
            buttonPopup.setOnClickListener{
                // Dismiss the popup window
                /*RetrofitClient.instance.recoveryPass(correo).enqueue(object: Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    )

                })*/
                popupWindow.dismiss()
            }

            // Set a dismiss listener for popup window
            popupWindow.setOnDismissListener {
                Toast.makeText(applicationContext,"Popup closed",Toast.LENGTH_SHORT).show()
            }


            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(login_root)
            popupWindow.showAtLocation(
                login_root, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
        }

        btnLogin.setOnClickListener {

            val email = txtEmail.text.toString().trim()
            val password = txtPasswd.text.toString().trim()

            if(email.isEmpty()){
                txtPasswd.error = "Email required"
                txtPasswd.requestFocus()
                return@setOnClickListener
            }else{

            }


            if(password.isEmpty()){
                txtEmail.error = "Password required"
                txtEmail.requestFocus()
                return@setOnClickListener
            }

            val pass16 = encode16(password)
            progressBar.show(context, "Iniciando Sesión....")
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                RetrofitClient.instance.userLogin(email, pass16)
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            progressBar.dialog.dismiss()
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {

                            if (response.body()?.exist?.equals(true) ?: (true === null)) {

                                progressBar.dialog.dismiss()
                                SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)
                                /*Toast.makeText(
                                    applicationContext,
                                    response.toString(),
                                    Toast.LENGTH_LONG
                                ).show()*/


                                val intent = Intent(applicationContext, HomeActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)


                            } else {

                                progressBar.dialog.dismiss()
                                //Toast.makeText(applicationContext, "Error, no se pudo acceder al usuario", Toast.LENGTH_SHORT).show()
                                var cons = AlertDialog.Builder(this@LoginActivity)

                                cons.setTitle("Error")
                                cons.setMessage("No se pudo iniciar sesión, usuario y/o contraseña inválidos")
                                cons.setPositiveButton("OK") { _, _ ->
                                    txtPasswd.setText("")
                                    txtPasswd.requestFocus()
                                }
                                val dialog: AlertDialog = cons.create()
                                dialog.show()
                            }

                        }
                    })
            }else{
                progressBar.dialog.dismiss()
                var cons = AlertDialog.Builder(this@LoginActivity)

                cons.setTitle("Error")
                cons.setMessage("Debe escibir correctamente su correo electrónico")
                cons.setPositiveButton("OK") { _, _ ->
                    txtPasswd.setText("")
                    txtEmail.setText("")
                    txtEmail.requestFocus()
                }
                val dialog: AlertDialog = cons.create()
                dialog.show()
            }

        } //Button of login



        btnCreate?.setOnClickListener {
            val intent = Intent(applicationContext, RegistryUserActivity::class.java)
            startActivity(intent)
        }



    }

    private fun encode16(password: String): String {
        var charset = Charsets.UTF_8
        var byteArray = password.toByteArray(charset)
        var encoded = ""
        for (b in byteArray) {
            val st = String.format("%02X", b)
            encoded = encoded.plus(st)
        }
        return encoded
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }




}
