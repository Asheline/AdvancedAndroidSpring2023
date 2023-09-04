package com.example.advancedandroidspring2023

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.advancedandroidspring2023.databinding.FragmentRemoteMessageBinding
import com.example.advancedandroidspring2023.databinding.FragmentWeatherStationBinding
import com.example.advancedandroidspring2023.datatypes.weatherstation.WeatherStation
import com.google.gson.GsonBuilder
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 * Use the [WeatherStationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RemoteMessageFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentRemoteMessageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // this could be in BuildConfig too

    var HIVEMQ_BROKER = "dcddd118718c4e7c858bb68113e86216.s2.eu.hivemq.cloud"
    var HIVEMQ_USERNAME = "SofieVaisanen"
    var HIVEMQ_PASSWORD = "maapallo123"
    var HIVEMQ_TOPIC = "test/topic"

    private lateinit var client: Mqtt3AsyncClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemoteMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // the binding -object allows you to access views in the layout, textviews etc.

        // version 3, IBM Cloud, weather station
        client = MqttClient.builder()
            .useMqttVersion3()
            .sslWithDefaultConfig()
            .identifier("dasdsadsadsadasdada")
            .serverHost(HIVEMQ_BROKER)
            .serverPort(8883)
            .buildAsync()

        client.connectWith()
            .simpleAuth()
            .username(HIVEMQ_USERNAME)
            .password(HIVEMQ_PASSWORD.toByteArray())
            .applySimpleAuth()
            .send()
            .whenComplete { connAck: Mqtt3ConnAck?, throwable: Throwable? ->
                if (throwable != null) {
                    Log.d("ADVTECH", "Connection failure.")
                } else {
                    // Setup subscribes or start publishing
                    subscribeToTopic()
                }
            }

        // kun nappia painetaan, lähetetään samaan topiciin satunnainen Hello World-viesti
        // jos Random näkyy punaisella, käy poistamassa ylhäältä java.util.* import
        // ja importaa Kotlinin versio Randomista
        binding.buttonSendCloudMessage.setOnClickListener {
            var randomNumber = Random.nextInt(0, 100)
            var stringPayload = "Hello world! " + randomNumber.toString()

            client.publishWith()
                .topic(HIVEMQ_TOPIC)
                .payload(stringPayload.toByteArray())
                .send()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        client.disconnect()
    }

    fun subscribeToTopic()
    {

        val gson = GsonBuilder().setPrettyPrinting().create()
        client.subscribeWith()
            .topicFilter(HIVEMQ_TOPIC)
            .callback { publish ->

                // this callback runs everytime your code receives new data payload


                var result = String(publish.getPayloadAsBytes())
                Log.d("ADVTECH", result)


                // jotta koodi varmasti ajaa binding-layeria koskevan
                // koodin oikeassa threadissa, käytetään runOnUiThreadia:
                activity?.runOnUiThread {
                    binding.textViewCloudMessage.text = result
                }

            }
            .send()
            .whenComplete { subAck, throwable ->
                if (throwable != null) {
                    // Handle failure to subscribe
                    Log.d("ADVTECH", "Subscribe failed.")
                } else {
                    // Handle successful subscription, e.g. logging or incrementing a metric
                    Log.d("ADVTECH", "Subscribed!")
                }
            }
    }}

