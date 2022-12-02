package com.example.vcubmonitor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vcubmonitor.json.synchDataJson
import com.example.vcubmonitor.json.VolleyResultCallBack
import com.example.vcubmonitor.models.ApiOpenTbm
import utils.Constant
import com.example.vcubmonitor.models.Station
import kotlin.properties.Delegates


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), VolleyResultCallBack{
    private lateinit var status : String
    private lateinit var name : String
    private var nbBikeAvailable by Delegates.notNull<Int>()
    private var nbPlaceAvailable by Delegates.notNull<Int>()
    private var nbVelosElectrique by Delegates.notNull<Int>()
    private var nbVeloClassiq by Delegates.notNull<Int>()
    private lateinit var listViewData: ListView
    private  lateinit var  textViewTitle : TextView
//    private var param1: String? = null
//    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewData = view.findViewById(R.id.listViewData)
        textViewTitle = view.findViewById(R.id.textViewTitle)
        val title: String = "Les Stations"
        textViewTitle.setText(title)
        //synchronise Json
        synchDataJson.syncData(requireContext(), Constant.URL, ApiOpenTbm::class.java,this)

    }

    override fun onVolleyResultListener(response: Any?) {
        val jsonTbm = response as ApiOpenTbm

        val stationList = mutableListOf<Station>(
        )

        for(i in 0 until jsonTbm.records.size){
            status = jsonTbm.records.get(i).fields.etat
            name = jsonTbm.records.get(i).fields.nom
            nbBikeAvailable = jsonTbm.records.get(i).fields.nbVeloTotal
            nbPlaceAvailable =jsonTbm.records.get(i).fields.nbPlaces
            nbVelosElectrique = jsonTbm.records.get(i).fields.nbVeloElec
            nbVeloClassiq = jsonTbm.records.get(i).fields.nbVeloClassic

            val myStation = Station(
                name,
                status,
                nbBikeAvailable,
                nbPlaceAvailable,
                nbVelosElectrique,
                nbVeloClassiq,
            )
            stationList.add(i,myStation)





//            status.set(i,jsonTbm.records.get(i).fields.etat)
//            name.set(i,jsonTbm.records.get(i).fields.nom)
//            nbBikeAvailable.set(i,jsonTbm.records.get(i).fields.nbVeloTotal)
//            nbPlaceAvailable.set(i,jsonTbm.records.get(i).fields.nbPlaces)
//            nbVelosElectrique.set(i,jsonTbm.records.get(i).fields.nbVeloElec)
//            nbVeloClassiq.set(i,jsonTbm.records.get(i).fields.nbVeloClassic)
        }


        listViewData.adapter = StationAdapter(
            requireContext(),
            R.layout.item_station,
            stationList
        )
        listViewData.setOnItemClickListener { parent, view, position, id ->
            val item = stationList.get(position)

            val intentDetails = Intent(requireContext(), DetailsActivity::class.java)

            // TODO : envoyer les informations (titre, catégorie etc ...)
            /*
        intentDetails.putExtra("title", item.name)
        intentDetails.putExtra("category", item.category)
        */
            intentDetails.putExtra("station", item)

            startActivity(intentDetails)

        }
    }

    override fun onVolleyErrorListener(error: Any?) {
        TODO("Not yet implemented")
    }
}