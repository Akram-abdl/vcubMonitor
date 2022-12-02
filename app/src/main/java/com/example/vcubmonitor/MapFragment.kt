package com.example.vcubmonitor

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vcubmonitor.json.VolleyResultCallBack
import com.example.vcubmonitor.json.synchDataJson
import com.example.vcubmonitor.models.ApiOpenTbm
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import utils.Constant

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment(), VolleyResultCallBack {
    private lateinit var lat: String
    private lateinit var lon: String
    private lateinit var name: String

    private val CONNECTED = "CONNECTEE"
    private val DISCONNECTED = "DECONNECTEE"
    private val MAINTENANCE = "MAINTENANCE"

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lat = "44.837789"
        lon = "-0.57918"
        name = "Bordeaux"
    }

    private val callback = OnMapReadyCallback { gMap ->
        googleMap = gMap

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat.toDouble(), lon.toDouble()), 12f))

        synchDataJson.syncData(requireContext(), Constant.URL, ApiOpenTbm::class.java,this)

        googleMap.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val context: Context? = context

                val info = LinearLayout(context)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(context)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(context)
                snippet.setTextColor(Color.GRAY)
                snippet.text = marker.snippet
                info.addView(title)
                info.addView(snippet)
                return info
            }
        })

        googleMap.setOnMarkerClickListener { marker ->
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))

            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        //synchronise Json
    }

    private fun addGoogleMapMarker(
        lat: Double,
        lon: Double,
        status: String,
        name: String,
        nbBikeAvailable: Int,
        nbPlaceAvailable: Int,
    ) {
        val latLng = LatLng(lat, lon)
        val totalPlace = nbBikeAvailable + nbPlaceAvailable

        Log.i("name", name)

        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(getString(R.string.maps_marker_title, name))
                .snippet(getString(R.string.maps_marker_bike_available, nbBikeAvailable.toString())+"\n"
                        +getString(R.string.maps_marker_place_available, nbPlaceAvailable.toString())+"\n"
                        +getString(R.string.maps_marker_total_place, totalPlace.toString()))
                .icon(BitmapDescriptorFactory.defaultMarker(getMarkerColor(status)))
        )
    }

    private fun getMarkerColor(status: String): Float {
        return when(status) {
            CONNECTED -> BitmapDescriptorFactory.HUE_GREEN
            MAINTENANCE -> BitmapDescriptorFactory.HUE_ORANGE
            DISCONNECTED -> BitmapDescriptorFactory.HUE_RED
            else -> BitmapDescriptorFactory.HUE_BLUE
        }
    }

    override fun onVolleyResultListener(response: Any?) {
        val jsonTbm = response as ApiOpenTbm
        for(i in 0 until jsonTbm.records.size){
            addGoogleMapMarker(
                jsonTbm.records[i].fields.geometry[1],
                jsonTbm.records[i].fields.geometry[0],
                jsonTbm.records[i].fields.etat,
                jsonTbm.records[i].fields.nom,
                jsonTbm.records[i].fields.nbVeloTotal,
                jsonTbm.records[i].fields.nbPlaces
            )
        }
    }

    override fun onVolleyErrorListener(error: Any?) {
        TODO("Not yet implemented")
    }
}