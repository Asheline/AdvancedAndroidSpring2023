package com.example.advancedandroidspring2023

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.advancedandroidspring2023.databinding.FragmentFeedbackReadBinding
import com.example.advancedandroidspring2023.datatypes.feedback.Feedback
import com.google.gson.GsonBuilder
import org.json.JSONObject


// FeedbackReadFragment, testataan tuleeko Directusin data perille
// vaihda JSON_URL:ssa tilalle oman Directus-kantasi URL
// ks. Harjoitus 3 -> Directus

class FeedbackReadFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentFeedbackReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedbackReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // the binding -object allows you to access views in the layout, textviews etc.

        getFeedbacks()

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var feedbacks : List<Feedback> = emptyList();

    fun getFeedbacks(){
        // this is the url where we want to get our data
        // Note: if using a local server, use http://10.0.2.2 for localhost. this is a virtual address for Android emulators, since
        // localhost refers to the Android device instead of your computer
        val JSON_URL = "https://y39u8hw7.directus.app/items/feedback?access_token=Cu5G61UOz7Qde_M-BVKAK5KZWUXn_3RG"
        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->
                // Log.d("ADVTECH", response)

                // haetaan JSONista data-kenttä, joka syötetään sitten GSONille
                val jObject = JSONObject(response)
                val jArray = jObject.getJSONArray("data")

                feedbacks = gson.fromJson(jArray.toString() , Array<Feedback>::class.java).toList()

                for(item in feedbacks) {
                    Log.d("ADVTECH", item.name.toString())
                }

                // response from API, you can use this in TextView, for example
                // Check also out the example below

                // Note: if you send data to API instead, this might not be needed

                val adapter = ArrayAdapter(activity as Context, R.layout.simple_list_item_1, feedbacks)
                binding.listviewFeedbacks.adapter = adapter
            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("ADVTECH", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                // we have to specify a proper header, otherwise Apigility will block our queries!
                // define we are after JSON data!
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}



