package com.proyect.avaclick.activities.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.skyfishjy.library.RippleBackground
import java.util.*

import com.proyect.avaclick.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var placesClient: PlacesClient? = null
    private var predictionList: List<AutocompletePrediction>? = null
    private var mLastKnownLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private var materialSearchBar: MaterialSearchBar? = null
    private var mapView: View? = null
    private var rippleBg: RippleBackground? = null
    private val DEFAULT_ZOOM = 15f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mapView = mapFragment.view
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        Places.initialize(this@MapsActivity, getString(R.string.google_maps_api))
        placesClient = Places.createClient(this)
        val token = AutocompleteSessionToken.newInstance()
        materialSearchBar?.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}
            override fun onSearchConfirmed(text: CharSequence) {
                startSearch(text.toString(), true, null, true)
            }

            override fun onButtonClicked(buttonCode: Int) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) { //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar!!.disableSearch()
                }
            }
        })
        materialSearchBar?.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val predictionsRequest = FindAutocompletePredictionsRequest.builder()
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .setSessionToken(token)
                    .setQuery(s.toString())
                    .build()
                placesClient!!.findAutocompletePredictions(predictionsRequest).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val predictionsResponse = task.result
                        if (predictionsResponse != null) {
                            predictionList = predictionsResponse.autocompletePredictions
                            val suggestionsList: MutableList<String?> = ArrayList()
                            for (i in predictionList!!.indices) {
                                val prediction = predictionList!![i]
                                suggestionsList.add(prediction.getFullText(null).toString())
                            }
                            materialSearchBar!!.updateLastSuggestions(suggestionsList)
                            if (!materialSearchBar!!.isSuggestionsVisible()) {
                                materialSearchBar!!.showSuggestionsList()
                            }
                        }
                    } else {
                        Log.i("mytag", "prediction fetching task unsuccessful")
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        materialSearchBar?.setSuggestionsClickListener(object : SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemClickListener(position: Int, v: View) {
                if (position >= predictionList!!.size) {
                    return
                }
                val selectedPrediction = predictionList!![position]
                val suggestion = materialSearchBar!!.getLastSuggestions()[position].toString()
                materialSearchBar!!.setText(suggestion)
                Handler().postDelayed({ materialSearchBar!!.clearSuggestions() }, 1000)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(materialSearchBar!!.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY)
                val placeId = selectedPrediction.placeId
                val placeFields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.LAT_LNG)
                val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()
                placesClient!!.fetchPlace(fetchPlaceRequest).addOnSuccessListener { fetchPlaceResponse ->
                    val place = fetchPlaceResponse.place
                    Log.i("mytag", "Place found: " + place.name)
                    val latLngOfPlace = place.latLng
                    if (latLngOfPlace != null) {
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM))
                    }
                }.addOnFailureListener { e ->
                    if (e is ApiException) {
                        val apiException = e
                        apiException.printStackTrace()
                        val statusCode = apiException.statusCode
                        Log.i("mytag", "place not found: " + e.message)
                        Log.i("mytag", "status code: $statusCode")
                    }
                }
            }

            override fun OnItemDeleteListener(position: Int, v: View) {}
        })

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.isMyLocationEnabled = true
        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        if (mapView != null && mapView!!.findViewById<View?>("1".toInt()) != null) {
            val locationButton = (mapView!!.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 0, 40, 180)
        }
        //check if gps is enabled or not and then request user to enable it
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(this@MapsActivity)
        val task = settingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this@MapsActivity) { deviceLocation }
        task.addOnFailureListener(this@MapsActivity) { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this@MapsActivity, 51)
                } catch (e1: IntentSender.SendIntentException) {
                    e1.printStackTrace()
                }
            }
        }
        mMap!!.setOnMyLocationButtonClickListener {
            if (materialSearchBar!!.isSuggestionsVisible) materialSearchBar!!.clearSuggestions()
            if (materialSearchBar!!.isSearchEnabled) materialSearchBar!!.disableSearch()
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 51) {
            if (resultCode == Activity.RESULT_OK) {
                deviceLocation
            }
        }
    }

    @get:SuppressLint("MissingPermission")
    private val deviceLocation: Unit
        private get() {
            mFusedLocationProviderClient!!.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mLastKnownLocation = task.result
                        if (mLastKnownLocation != null) {
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLastKnownLocation!!.latitude, mLastKnownLocation!!.longitude), DEFAULT_ZOOM))
                        } else {
                            val locationRequest = LocationRequest.create()
                            locationRequest.interval = 10000
                            locationRequest.fastestInterval = 5000
                            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                            locationCallback = object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    if (locationResult == null) {
                                        return
                                    }
                                    mLastKnownLocation = locationResult.lastLocation
                                    // mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM))
                                    mFusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
                                }
                            }
                            mFusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
                        }
                    } else {
                        Toast.makeText(this@MapsActivity, "unable to get last location", Toast.LENGTH_SHORT).show()
                    }
                }
        }
}
