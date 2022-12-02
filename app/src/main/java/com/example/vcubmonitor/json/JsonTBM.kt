package com.example.vcubmonitor.json

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vcubmonitor.models.ApiOpenTbm
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.internal.Primitives
import com.google.gson.reflect.TypeToken

object JsonTBM{
    //private lateinit var nameVille : String

    fun <T> syncData(mContext: Context, url : String, classOfT: Class<T>?, resultCallBack: VolleyResultCallBack){
        val queue = Volley.newRequestQueue(mContext)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { json ->
                Log.i("JSON", "succès: $json")
                resultCallBack.onVolleyResultListener(parseJson(json, classOfT))
            },
            Response.ErrorListener { error ->
                val json = String(error.networkResponse.data)
                Log.i("JSON", "erreur: $json")
                resultCallBack.onVolleyErrorListener(parseJson(json, classOfT))
            })
        queue.add(stringRequest)
    }


    private fun <T> parseJson(json: String?, classOfT: Class<T>? ) : Any? {
        val apiTBM = Gson().fromJson(json, classOfT)
        return apiTBM
       // nameVille = apiTBM.records.get(0).fields.nom
    }
}
