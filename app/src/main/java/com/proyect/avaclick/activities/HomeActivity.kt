package com.proyect.avaclick.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.proyect.avaclick.R
import com.proyect.avaclick.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        cerrarSesion.setOnClickListener {
            SharedPrefManager.getInstance(applicationContext).clear()

            val intent:Intent = Intent(this, LoginActivity::class.java)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnConsultar?.setOnClickListener {
            val intent = Intent(applicationContext, ReportListActivity::class.java)
            startActivity(intent)
        }

    }




    fun clseSesion(){
        SharedPrefManager.getInstance(applicationContext).clear()

        val intent:Intent = Intent(this, LoginActivity::class.java)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    override fun onStart() {
        super.onStart()

      if(!SharedPrefManager.getInstance(this).isLoggedIn){
          val intent = Intent(applicationContext, LoginActivity::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          startActivity(intent)

      }
    }
}
