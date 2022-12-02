package com.example.vcubmonitor.json

import com.android.volley.VolleyError

interface VolleyResultCallBack {
    fun onVolleyResultListener(response: Any?)
    fun onVolleyErrorListener(error :Any?)
}