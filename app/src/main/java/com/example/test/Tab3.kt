package com.example.test

import android.app.ActivityManager
import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil.setContentView
import com.bumptech.glide.Glide
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class Tab3 : Fragment() {
    private var mContext : Context? = null
    private val MYREQUESTCODE : Int = 3000

    override fun onAttach(context : Context){
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
            if (mContext == null){
                throw java.lang.IllegalArgumentException("mContext is null")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun gifSpeed(percentage:Float): Float {
        var speed = (percentage / 50) * 1.2
        if (speed > 0)
            return speed.toFloat()
        else
            return 0.1f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab3, container, false)

        var text = view.findViewById<TextView>(R.id.test)
        var btn = view.findViewById<ImageButton>(R.id.imageButton)
//        var gif = GifDrawable(resources, R.drawable.runner_2)
        var gif = GifDrawable(resources, R.raw.runner_1)
        var gif_img = view.findViewById<GifImageView>(R.id.gif)
        gif_img.setImageDrawable(gif)
        gif_img.bringToFront()

        val memoryInfo = ActivityManager.MemoryInfo()
        (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val nativeHeapSize = memoryInfo.totalMem
        val nativeHeapFreeSize = memoryInfo.availMem
        val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize
        val usedMemInPercentage = usedMemInBytes * 100 / nativeHeapSize

        gif.setSpeed(gifSpeed(usedMemInPercentage.toFloat()))
        text.text= usedMemInPercentage.toString()+"%"

        btn.setOnClickListener{
            val memoryInfo_2 = ActivityManager.MemoryInfo()
            (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo_2)
            val nativeHeapSize_2 = memoryInfo_2.totalMem
            val nativeHeapFreeSize_2 = memoryInfo_2.availMem
            val usedMemInBytes_2 = nativeHeapSize_2 - nativeHeapFreeSize_2
            val usedMemInPercentage_2 = usedMemInBytes_2 * 100 / nativeHeapSize_2

            gif.setSpeed(gifSpeed(usedMemInPercentage_2.toFloat()))
            text.text= usedMemInPercentage_2.toString()+"%"
        }
        return view
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        getWeather()
    }
    private fun checkPermission(permissions: Array<String>): Boolean { return permissions.all { ActivityCompat.checkSelfPermission(mContext!!, it) == PackageManager.PERMISSION_GRANTED } }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:Array<String>, grantResults:IntArray){
        when (requestCode) {
            MYREQUESTCODE -> {
                if (checkPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))) {
                    Log.d("where", "accepted!")
                    getWeather()
                }else{
                    Log.d("where","permission denied by user")
                }
            }
        }
    }

    private fun getWeather(){
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        lateinit var lat: String
        lateinit var lon: String
        if (!checkPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), MYREQUESTCODE)
        }else{
            Log.d("where","inGetWeather()")
            //succeed after get the location infos
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Location 값 받아오기
                    lat = location?.latitude.toString()
                    lon = location?.longitude.toString()
                    Log.d("where","lat $lat lon $lon")

                    weatherTask(lat,lon).execute()
                }
        }

    }


    inner class weatherTask(val lat:String,val lon:String):AsyncTask<String,Void,MutableList<String>>(){
        val API : String ="96585e8e3a8a084ad12fd0d38fc8caec"

        override fun doInBackground(vararg p0: String?): MutableList<String> {
            var responseFc :String?
            var responseCur :String?
            val responses : MutableList<String> = ArrayList<String>()
            try{
                responseFc = URL("https://api.openweathermap.org/data/2.5/forecast?units=metric&lat=$lat&lon=$lon&appid=$API").readText(Charsets.UTF_8)
                responseCur = URL("https://api.openweathermap.org/data/2.5/weather?units=metric&lat=$lat&lon=$lon&appid=$API").readText(Charsets.UTF_8)
                responses.add(responseFc)
                responses.add(responseCur)
            }catch (e:Exception){
                responseFc =  null
                responseCur = null
            }
            return responses
        }

        override fun onPostExecute(result:MutableList<String>){
            super.onPostExecute(result)
            try{
                /* Extracting JSON returns from the API */
                Log.d("where", "result : $result")
                val jsonObjFc = JSONObject(result[0])
                val jsonObjCur = JSONObject(result[1])

                val listFc = jsonObjFc.getJSONArray("list")
                for (i:Int in 0..2){
                    setWeatherFcOnView(listFc,i)
                }
                setWeatherCurOnView(jsonObjCur)
                view?.findViewById<RelativeLayout>(R.id.tab3_weatherfc_container)?.visibility =View.VISIBLE
            }catch (e:Exception){
                Log.d("where",e.toString())
                view?.findViewById<TextView>(R.id.tab3_error)?.visibility = View.VISIBLE
            }
        }

        private fun setWeatherFcOnView(weatherList : JSONArray, idx : Int){
            val layoutId : String = "tab3_interval${idx}_"
            Log.d("where", "layoutId :$layoutId")
            val interval = weatherList.getJSONObject(idx)
            val time = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(interval.getLong("dt")*1000))
            val temp = interval.getJSONObject("main").getString("temp")+"°C"
            val weatherDescription= interval.getJSONArray("weather").getJSONObject(0).getString("description")
            val weatherIcon= interval.getJSONArray("weather").getJSONObject(0).getString("icon")
            val pop = interval.getString("pop")
            Log.d("where",weatherDescription)
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}time","id",requireActivity().packageName))?.setText(time)
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}temp","id",requireActivity().packageName))?.text=temp
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_description","id",requireActivity().packageName))?.text=weatherDescription
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_icon","id",requireActivity().packageName))?.text=weatherIcon
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}pop","id",requireActivity().packageName))?.text=pop
        }

        private fun setWeatherCurOnView(weatherCur :JSONObject){
            val layoutId : String = "tab3_weathercur_"
            val time = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(weatherCur.getLong("dt")*1000))
            val temp = weatherCur.getJSONObject("main").getString("temp")+"°C"
            val weatherDescription= weatherCur.getJSONArray("weather").getJSONObject(0).getString("description")
            val weatherIcon= weatherCur.getJSONArray("weather").getJSONObject(0).getString("icon")
            var weatherId= weatherCur.getJSONArray("weather").getJSONObject(0).getInt("id")
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}time","id",requireActivity().packageName))?.setText(time)
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}temp","id",requireActivity().packageName))?.text=temp
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_description","id",requireActivity().packageName))?.text=weatherDescription
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_icon","id",requireActivity().packageName))?.text=weatherIcon
            weatherId = 801
            when (weatherId){
                601, 602->{ //snows
                    view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.snow2)
                }
                801, 802->{ //some clouds
                    view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.cloud)
                }

            }
        }


    }

}
