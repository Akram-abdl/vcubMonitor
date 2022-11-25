package com.example.vcubmonitor

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
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

        //TODO Make a loop when we will have the method to call the API
        addGoogleMapMarker("44.83784", "-0.59028", MAINTENANCE,"ST Bruno", 34, 10)
        addGoogleMapMarker( "44.83803", "-0.58437", CONNECTED,"Meriadeck", 12, 2)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat.toDouble(), lon.toDouble()), 12f))

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
    }

    fun addGoogleMapMarker(
        lat: String,
        lon: String,
        status: String,
        name: String,
        nbBikeAvailable: Int,
        nbPlaceAvailable: Int,
    ) {
        val latLng = LatLng(lat.toDouble(), lon.toDouble())
        val totalPlace = nbBikeAvailable + nbPlaceAvailable

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

    fun getMarkerColor(status: String): Float {
        return when(status) {
            CONNECTED -> BitmapDescriptorFactory.HUE_GREEN
            MAINTENANCE -> BitmapDescriptorFactory.HUE_ORANGE
            DISCONNECTED -> BitmapDescriptorFactory.HUE_RED
            else -> BitmapDescriptorFactory.HUE_BLUE
        }
    }
}