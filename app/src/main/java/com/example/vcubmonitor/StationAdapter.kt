package com.example.vcubmonitor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.vcubmonitor.models.Station

class StationAdapter(context: Context, private val resource: Int, objects: MutableList<Station>)
    : ArrayAdapter<Station>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var convertView = convertView
        val myViewHolder: ViewHolder // déclaration

        if(convertView == null) {
            Log.e("StationAdapter", "convertView IF")
            // afficher le layout item_restaurant
            convertView = LayoutInflater.from(context).inflate(resource, null)

            myViewHolder = ViewHolder() // instance

//            myViewHolder.textViewTitle = convertView!!.findViewById(R.id.textViewTitle)

            myViewHolder.textViewNameList = convertView.findViewById(R.id.textViewNameList)
            myViewHolder.textViewStateList = convertView!!.findViewById(R.id.textViewStateList)
            myViewHolder.textViewBikesAvailableList = convertView!!.findViewById(R.id.textViewBikesAvailableList)
//            myViewHolder.textViewElecBikes = convertView!!.findViewById(R.id.textViewElecBikes)
//            myViewHolder.textViewClassiqBikes = convertView!!.findViewById(R.id.textViewClassiqBikes)



            // enregistrement de l'instance ViewHolder
            // convertView.setTag(myViewHolder) // en java
            convertView.tag = myViewHolder // en kotlin
        } else {
            myViewHolder = convertView.tag as ViewHolder
            Log.e("StationAdapter", "convertView ELSE")
        }

        // afficher les données dans textViewTitle et textViewCategory
        val item/*: Station*/ = getItem(position)/* as Station*/

        myViewHolder.textViewNameList?.setText(item!!.nom)
        myViewHolder.textViewStateList?.setText(item!!.etat)
        myViewHolder.textViewBikesAvailableList?.setText(item!!.nbvelos.toString())
//        myViewHolder.textViewElecBikes?.setText(item!!.nbelec)
//        myViewHolder.textViewClassiqBikes?.setText(item!!.nbclassiq)
        // affichage de l'image
        // PICASSO / GLIDE / FRESCO
//        Picasso.get()
//            .load(item?.image)
//            .into(myViewHolder.imageViewPhoto)

        return convertView
    }

    class ViewHolder {
//        var textViewTitle: TextView? = null
//        var textViewCategory: TextView? = null
//        var textViewSession: TextView? = null
//        var imageViewPhoto: ImageView? = null

        var textViewNameList: TextView? = null
        var textViewStateList: TextView? = null
        var textViewBikesAvailableList: TextView? = null
//        var textViewElecBikes: TextView? = null
//        var textViewClassiqBikes: TextView? = null

    }
}