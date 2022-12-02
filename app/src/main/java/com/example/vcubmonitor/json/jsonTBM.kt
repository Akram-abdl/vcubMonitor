package com.example.vcubmonitor.json

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import utils.Constant
import androidx.fragment.app.Fragment

class jsonTBM : Fragment() {
  /*  // 4) requête HTTP avec Volley
    // Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(context)
    //val url = String.format(Constant.URL, "Bordeaux") //bordeaux en dur
    var url = Constant.URL
    // Request a string response from the provided URL.
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        Response.Listener<String> { json ->
            Log.i("JSON", "succès: $json")

            // parseJson(json)
        },
        Response.ErrorListener { error ->
            val json = String(error.networkResponse.data)
            Log.i("JSON", "erreur: $json")

            //  parseJson(json)
        })

    // Add the request to the RequestQueue.
    queue.add(stringRequest)
*/}
