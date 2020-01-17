package com.proyect.avaclick.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.proyect.avaclick.R
import com.proyect.avaclick.activities.fragments.MapsActivity
import com.proyect.avaclick.activities.fragments.PermissionsActivity
import com.proyect.avaclick.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private var accountHeader: AccountHeader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        accountHeader?.addProfiles(
            ProfileDrawerItem()
                .withName(SharedPrefManager.getInstance(this).nameUser)
                .withIcon(
                    IconicsDrawable(
                        this@HomeActivity,
                        GoogleMaterial.Icon.gmd_account_circle
                    ).color(Color.WHITE).actionBar()
                )
        )
        accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.mipmap.fondo)
            .withOnAccountHeaderListener { view: View?, profile: IProfile<*>?, currentProfile: Boolean -> false }
            .build()

        /*val profile = ProfileDrawerItem().withName(
            SharedPrefManager.getInstance(this).nameUser)
            .withIcon(IconicsDrawable(this@HomeActivity, GoogleMaterial.Icon.gmd_account_circle).color(Color.WHITE).actionBar())

        accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withTranslucentStatusBar(true)
            .withHeaderBackground(R.mipmap.fondo)
            .withOnAccountHeaderListener { view: View?, profile: IProfile<*>?, currentProfile: Boolean -> false }
            .withTextColor(Color.BLACK)
            .addProfiles(profile)
            .withSavedInstance(savedInstanceState)
            .build()*/

        val drawer: Drawer = DrawerBuilder(this)
            .withToolbar(toolbar)
            .withAccountHeader(accountHeader)
            .withSliderBackgroundColorRes(R.color.fond)
            .addDrawerItems(
                PrimaryDrawerItem().withName("Menu").withTextColor(Color.BLACK).withSelectedTextColor(
                    Color.BLACK
                ),
                //new SecondaryDrawerItem().withName("Actualizar").withTextColor(Color.WHITE).withIconColorRes(R.color.orange).withIcon(R.mipmap.user).withIdentifier(50).withCheckable(false),
                PrimaryDrawerItem().withName("Perfil").withTextColor(
                    Color.WHITE
                ).withIconColorRes(R.color.orange).withIcon(R.mipmap.user).withIdentifier(1).withCheckable(
                    false
                ),
                SecondaryDrawerItem().withName("Cerrar sesi\u00f3n").withTextColor(Color.WHITE).withIconColorRes(
                    R.color.orange
                ).withIcon(R.mipmap.clouse).withIdentifier(60).withCheckable(false) // new PrimaryDrawerItem().withName("Cerrar sesi\u00f3n").withDescription("Cerrar sesissssfsfsfn").withIcon(R.mipmap.avaclick).withIdentifier(3).withCheckable(false)
            )
            .build()
        drawer.onDrawerItemClickListener = object : Drawer.OnDrawerItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
                drawerItem: IDrawerItem?
            ): Boolean {
                if (drawerItem != null) {
                    if(drawerItem.identifier === 1){
                        val intent = Intent(applicationContext, PerfilUserActivity::class.java)
                        startActivity(intent)
                    }
                }
                if (drawerItem is SecondaryDrawerItem) {
                        if ((drawerItem as SecondaryDrawerItem?)!!.name != null) {
                            if (drawerItem.name.equals("Cerrar sesi\u00f3n")) {
                                var cons = AlertDialog.Builder(this@HomeActivity)

                                cons.setTitle("Cerrando Sesión")
                                cons.setMessage("¿Desea Cerrar Sesión?")
                                cons.setNegativeButton("SI") { _, _ ->
                                    closeSession()
                                }
                                cons.setPositiveButton("NO"){_, _ ->

                                }
                                val dialog: AlertDialog = cons.create()
                                dialog.show()
                            }
                        }
                    }
                return false
            }
        }

        btnCrear.setOnClickListener{
            val intent = Intent(applicationContext, PermissionsActivity::class.java)
            startActivity(intent)
        }

        btnConsultar?.setOnClickListener {
            val intent = Intent(applicationContext, ReportListActivity::class.java)
            startActivity(intent)
        }

    }


    fun closeSession(){
        SharedPrefManager.getInstance(applicationContext).clear()

        val intent = Intent(this, LoginActivity::class.java)
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
