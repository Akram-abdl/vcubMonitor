package com.example.vcubmonitor.models

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Field

data class ApiOpenTbm(
    @SerializedName("records") val records: Array<ApiRecord>
)
{
    data class ApiRecord(
        @SerializedName("fields") val fields : ReccordsField
    )
    {
        data class ReccordsField(
            val nom: String,
            @SerializedName("nbvelos") val nbVeloTotal : Int,
            @SerializedName("nbplaces") val nbPlaces : Int,
            @SerializedName("nbelec") val nbVeloElec : Int,
            @SerializedName("nbclassiq") val nbVeloClassic : Int,
            val etat : String,
            @SerializedName("geo_point_2d") val geometry : Array<Double>
        )
         {
            data class ReccordsFieldGeometry(
                val coordonnees : Int,
            )
        }
    }
}
