package com.proyect.avaclick.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.proyect.avaclick.R
import com.proyect.avaclick.activities.LoginActivity
import java.io.File

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splashscreen)

        //4second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
            //finish this activity
            finish()
        },1500)



        fun deleteDir(dir: File?): Boolean {
            if (dir != null && dir.isDirectory) {
                val children = dir.list()
                for (i in children!!.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
                return dir.delete()
            } else return if (dir != null && dir.isFile) {
                dir.delete()
            } else {
                false
            }
        }

        fun deleteCache(context: Context) {
            try {
                val dir = context.cacheDir
                deleteDir(dir)
            } catch (e: Exception) {
            }

        }

    }


}