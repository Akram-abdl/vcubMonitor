package com.example.vcubmonitor.json

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

object synchDataJson{
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
    }
}
