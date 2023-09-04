package com.example.advancedandroidspring2023

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.advancedandroidspring2023.databinding.FragmentWeatherStationBinding
import com.example.advancedandroidspring2023.datatypes.weatherstation.WeatherStation
import com.google.gson.GsonBuilder
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [WeatherStationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherStationFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentWeatherStationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // this could be in BuildConfig too

    var MQTT_TOPIC = "iot-2/type/ws-10/id/3004/evt/data/fmt/json"
    var MQTT_BROKER = "i5u4t3.messaging.internetofthings.ibmcloud.com"
    var MQTT_PASSWORD = " iJt_MQqHikrls*)Cpc"
    var MQTT_CLIENT_ID = "a:i5u4t3:"
    var MQTT_USERNAME = "a-i5u4t3-kg2otp2blv"

    private lateinit var client: Mqtt3AsyncClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherStationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // the binding -object allows you to access views in the layout, textviews etc.

        // version 3, IBM Cloud, weather station
        client = MqttClient.builder()
            .useMqttVersion3()
            .sslWithDefaultConfig()
            .identifier(MQTT_CLIENT_ID + UUID.randomUUID().toString())
            .serverHost(MQTT_BROKER)
            .serverPort(8883)
            .buildAsync()

        client.connectWith()
            .simpleAuth()
            .username(MQTT_USERNAME)
            .password(MQTT_PASSWORD.toByteArray())
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
            .topicFilter(MQTT_TOPIC)
            .callback { publish ->

                // this callback runs everytime your code receives new data payload
                var result = String(publish.getPayloadAsBytes())
                Log.d("ADVTECH", result)


                try{
                var item : WeatherStation = gson.fromJson(result, WeatherStation::class.java)
                Log.d("ADVTECH", item.d.get1().v.toString() + "C")

                    var temperature = item.d.get1().v
                    var pressure = item.d.get2().v
                    var humidity = item.d.get3().v

                    var text = "Temperature: ${temperature}C \nPressure: \t\t${pressure} bar\nHumidity: ${humidity}%"

                    activity?.runOnUiThread { binding.textView3.text = text}


                }
                catch (e : Exception){
                Log.d("ADVTECH", e.message.toString())
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

