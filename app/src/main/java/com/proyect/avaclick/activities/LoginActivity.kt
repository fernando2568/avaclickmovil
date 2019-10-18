package com.proyect.avaclick.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.proyect.avaclick.R
import com.proyect.avaclick.api.RetrofitClient
import com.proyect.avaclick.models.LoginResponse
import com.proyect.avaclick.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnLogin.setOnClickListener {

            val email = txtEmail.text.toString().trim()
            val password = txtPasswd.text.toString().trim()

            if(email.isEmpty()){
                txtPasswd.error = "Email required"
                txtPasswd.requestFocus()
                return@setOnClickListener
            }


            if(password.isEmpty()){
                txtEmail.error = "Password required"
                txtEmail.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.userLogin(email, password)
                    .enqueue(object: Callback<LoginResponse>{
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if(response.body()?.success === true){

                                //SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)
                                Toast.makeText(applicationContext,response.toString(), Toast.LENGTH_LONG).show()
                                val intent = Intent(applicationContext, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)


                            }else{
                                Toast.makeText(applicationContext, response.body()?.success.toString(), Toast.LENGTH_LONG).show()
                            }

                        }
                    })

        }

        btnCreate?.setOnClickListener {
            val intent = Intent(this, RegistryUserActivity::class.java)
            startActivity(intent)
        }
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
