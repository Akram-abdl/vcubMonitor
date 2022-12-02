package com.example.vcubmonitor.models

import android.os.Parcel
import android.os.Parcelable
// TODO : add geoshape data
data class Station(
    val nom: String?,
    val etat: String?,
    val nbvelos: Int,
    val nbplaces: Int,
    val nbelec: Int,
    val nbclassiq: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(etat)
        parcel.writeInt(nbvelos)
        parcel.writeInt(nbplaces)
        parcel.writeInt(nbelec)
        parcel.writeInt(nbclassiq)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Station> {
        override fun createFromParcel(parcel: Parcel): Station {
            return Station(parcel)
        }

        override fun newArray(size: Int): Array<Station?> {
            return arrayOfNulls(size)
        }
    }
}