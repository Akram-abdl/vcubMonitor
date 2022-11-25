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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    private lateinit var lat: String
    private lateinit var lon: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lat = "44.837789"
        lon = "-0.57918"
        name = "Bordeaux"
    }

    private val callback = OnMapReadyCallback { googleMap ->

        addGoogleMapMarker(googleMap, "44.83784", "-0.59028", "CONNECTEE","ST Bruno", 34, 10)
        addGoogleMapMarker(googleMap, "44.83803", "-0.58437", "DECONNECTEE","Meriadeck", 12, 2)

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
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
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
        googleMap: GoogleMap,
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
        if (status === "CONNECTEE") {
            return BitmapDescriptorFactory.HUE_GREEN
        } else if (status === "MAINTENANCE") {
            return BitmapDescriptorFactory.HUE_ORANGE
        } else if(status === "DECONNECTEE") {
            return BitmapDescriptorFactory.HUE_RED
        }

        return BitmapDescriptorFactory.HUE_BLUE
    }
}