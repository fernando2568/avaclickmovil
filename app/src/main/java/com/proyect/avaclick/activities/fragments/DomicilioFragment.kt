package com.proyect.avaclick.activities.fragments

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.proyect.avaclick.R


 class DomicilioFragment : Fragment(), OnMapReadyCallback{

     private lateinit var lastLocation: Location

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
        return inflater.inflate(R.layout.fragment_domicilio, container, false)
    }

     override fun onMapReady(p0: GoogleMap?) {
         
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }


 }