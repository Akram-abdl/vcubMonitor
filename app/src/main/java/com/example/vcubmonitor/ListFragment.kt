package com.example.vcubmonitor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vcubmonitor.json.synchDataJson
import com.example.vcubmonitor.json.VolleyResultCallBack
import com.example.vcubmonitor.models.ApiOpenTbm
import utils.Constant
import com.example.vcubmonitor.models.Station



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
    private lateinit var status : Array<String>
    private lateinit var name : Array<String>
    private lateinit var nbBikeAvailable : Array<Int>
    private lateinit var nbPlaceAvailable : Array<Int>
    private lateinit var nbVelosElectrique : Array<Int>
    private lateinit var nbVeloClassiq : Array<Int>
    private lateinit var listViewData: ListView
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
        //synchronise Json
        synchDataJson.syncData(requireContext(), Constant.URL, ApiOpenTbm::class.java,this)
        listViewData = view.findViewById(R.id.listViewData)
        val stationList = mutableListOf<Station>(
            Station(
                "Avengers EndGame",
                "Action",
                12,
                15,
                16,
                6,
            )
        )
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

    override fun onVolleyResultListener(response: Any?) {
        val jsonTbm = response as ApiOpenTbm
        for(i in 0 until jsonTbm.records.size){
            status.set(i,jsonTbm.records.get(i).fields.etat)
            name.set(i,jsonTbm.records.get(i).fields.nom)
            nbBikeAvailable.set(i,jsonTbm.records.get(i).fields.nbVeloTotal)
            nbPlaceAvailable.set(i,jsonTbm.records.get(i).fields.nbPlaces)
            nbVelosElectrique.set(i,jsonTbm.records.get(i).fields.nbVeloElec)
            nbVeloClassiq.set(i,jsonTbm.records.get(i).fields.nbVeloClassic)
        }
    }

    override fun onVolleyErrorListener(error: Any?) {
        TODO("Not yet implemented")
    }
}