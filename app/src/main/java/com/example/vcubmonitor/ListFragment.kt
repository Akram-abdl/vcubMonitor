package com.example.vcubmonitor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.vcubmonitor.json.synchDataJson
import com.example.vcubmonitor.json.VolleyResultCallBack
import com.example.vcubmonitor.models.ApiOpenTbm
import utils.Constant


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), VolleyResultCallBack{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //synchronise Json
        synchDataJson.syncData(requireContext(), Constant.URL, ApiOpenTbm::class.java,this)

    }

    override fun onVolleyResultListener(response: Any?) {
        val jsonTbm = response as ApiOpenTbm
        Log.i("JSON", "ma ville est : ${jsonTbm.records.get(0).fields.nom}/")

    }

    override fun onVolleyErrorListener(error: Any?) {
        TODO("Not yet implemented")
    }
}