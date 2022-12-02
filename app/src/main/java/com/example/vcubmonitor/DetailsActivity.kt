package com.example.vcubmonitor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.vcubmonitor.models.Station

class DetailsActivity : AppCompatActivity() {

    private val textViewName: TextView by lazy { findViewById<TextView>(R.id.textViewName) }
    private val textViewState: TextView by lazy { findViewById<TextView>(R.id.textViewState) }
    private val textViewBikesAvailable: TextView by lazy { findViewById<TextView>(R.id.textViewBikesAvailable) }
    private val textViewElecBikes: TextView by lazy { findViewById<TextView>(R.id.textViewElecBikes) }
    private val textViewClassiqBikes: TextView by lazy { findViewById<TextView>(R.id.textViewClassiqBikes) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        /*
        val title = intent.getStringExtra("title")
        val category = intent.getStringExtra("category")

        textViewTitle.setText(title)
        textViewCategory.setText(category)
        */

        //val item = intent.getSerializableExtra("restaurant") as Restaurant
        val item = intent.getParcelableExtra<Station>("station")

        textViewName.setText(item?.nom)
        textViewState.setText(item?.etat)
        if (item != null) {
            textViewBikesAvailable.setText(item.nbvelos.toString())
        }
        if (item != null) {
            textViewElecBikes.setText(item.nbelec.toString())
        }
        if (item != null) {
            textViewClassiqBikes.setText(item.nbclassiq.toString())
        }


        // affichage de l'image
        // PICASSO / GLIDE / FRESCO
//        Picasso.get()
//            .load(item?.image)
//            .into(imageViewPhoto)

//        buttonEmail.setOnClickListener {
//            val intentEmail = Intent(Intent.ACTION_SEND)
//            intentEmail.setType("message/rfc822")
//            //intent.putExtra(Intent.EXTRA_EMAIL, MutableCollection<String>(item?.email!!))
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Le sujet")
//            intent.putExtra(Intent.EXTRA_TEXT, "Le corps du message")
//
//            startActivity(Intent.createChooser(intentEmail, "Partage Email"))
//        }
//
//        buttonPhone.setOnClickListener {
//            val intentPhone = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item?.phone}"))
//            startActivity(Intent.createChooser(intentPhone, "Téléphoner"))
//
//            // TODO : gérer les permissions dangereuses
//        }
//
//        buttonSite.setOnClickListener {
//            val intentSite = Intent(Intent.ACTION_VIEW, Uri.parse(item?.url))
//            startActivity(Intent.createChooser(intentSite, "Navigateur web"))
//        }

    }
}