package com.proyect.avaclick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private var mAppBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_principal)

        val toolbar: Toolbar = findViewById(R.id.toolbar_menu)
        setSupportActionBar(toolbar)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home,R.id.nav_perfil, R.id.nav_sessionOff)
            .setDrawerLayout(drawer)
            .build()
        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, mAppBarConfiguration!!) || super.onSupportNavigateUp()
    }
}
