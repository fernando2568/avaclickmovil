package com.proyect.avaclick.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.proyect.avaclick.R
import androidx.appcompat.widget.Toolbar


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }


    override fun onStart() {
        super.onStart()

     // if(!SharedPrefManager.getInstance(this).isLoggedIn){
     //     val intent = Intent(applicationContext, LoginActivity::class.java)
     //     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
     //     startActivity(intent)
     // }
    }
}
