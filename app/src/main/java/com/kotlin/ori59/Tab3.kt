package com.kotlin.ori59

import android.app.ActivityManager
import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule


class Tab3 : Fragment() {
    private var mContext : Context? = null
    private val MYREQUESTCODE : Int = 3000
    private lateinit var ramUsageTv : TextView
    private lateinit var ramUsageDescriptionTv : TextView
    private lateinit var btnV : ImageButton
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

        var ramUsage = view.findViewById<TextView>(R.id.ram_usage)

        var btn = view.findViewById<ImageButton>(R.id.imageButton)
        var gif = GifDrawable(resources, R.raw.runner_1)
        var gif_img = view.findViewById<GifImageView>(R.id.gif)
        var touched_img = view.findViewById<ImageView>(R.id.touched_img)

        gif_img.setImageDrawable(gif)
        gif_img.bringToFront()

        touched_img.bringToFront()
        touched_img.setAlpha(0)

        touched_img.setOnClickListener {
            touched_img.setAlpha(255)
            gif_img.setAlpha(0)
            Timer().schedule(1000){
                touched_img.setAlpha(0)
                gif_img.setAlpha(255)
            }
        }

        val memoryInfo = ActivityManager.MemoryInfo()
        (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val nativeHeapSize = memoryInfo.totalMem
        val nativeHeapFreeSize = memoryInfo.availMem
        val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize
        val usedMemInPercentage = usedMemInBytes * 100 / nativeHeapSize

        gif.setSpeed(gifSpeed(usedMemInPercentage.toFloat()))
        ramUsage.text= usedMemInPercentage.toString()+"%"


        //save for night mode
        ramUsageTv=ramUsage
        ramUsageDescriptionTv = view.findViewById(R.id.ram_usage_description)
        btnV = btn

        btn.setOnClickListener{
            Log.d("where","BtnClkLstner")
            ramUsage.text="Loading..."
            /* weather update */
            val tempText = view?.findViewById<TextView>(R.id.temp_text)
            val mainweatherText = view?.findViewById<TextView>(R.id.mainweather_text)
            tempText?.text="Loading..."
            mainweatherText?.text=""
            view?.findViewById<TextView>(R.id.weather_update_time)?.text= ""
            getWeather()

            /* ramUsage update */


            gif_img.bringToFront()
            val memoryInfo_2 = ActivityManager.MemoryInfo()
            (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo_2)
            val nativeHeapSize_2 = memoryInfo_2.totalMem
            val nativeHeapFreeSize_2 = memoryInfo_2.availMem
            val usedMemInBytes_2 = nativeHeapSize_2 - nativeHeapFreeSize_2
            val usedMemInPercentage_2 = usedMemInBytes_2 * 100 / nativeHeapSize_2

            gif.setSpeed(gifSpeed(usedMemInPercentage_2.toFloat()))
            ramUsage.text= usedMemInPercentage_2.toString()+"%"

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
//        Log.d("where","inGetWeather()")
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
            Log.d("where","response :"+responses.toString())
            return responses
        }

        override fun onPostExecute(result:MutableList<String>){
            super.onPostExecute(result)
            try{
                /* Extracting JSON returns from the API */
//                val jsonObjFc = JSONObject(result[0])
                val jsonObjCur = JSONObject(result[1])

//                val listFc = jsonObjFc.getJSONArray("list")
//                for (i:Int in 0..2){
//                    setWeatherFcOnView(listFc,i)
//                }
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
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}time","id",requireActivity().packageName))?.setText(time)
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}temp","id",requireActivity().packageName))?.text=temp
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_description","id",requireActivity().packageName))?.text=weatherDescription
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_icon","id",requireActivity().packageName))?.text=weatherIcon
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}pop","id",requireActivity().packageName))?.text=pop
        }

        private fun setWeatherCurOnView(weatherCur :JSONObject){
            val layoutId : String = "tab3_weathercur_"
//            val time = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(weatherCur.getLong("dt")*1000))
            val time = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date())
            val temp = weatherCur.getJSONObject("main").getString("temp")+"°C"
            val weatherDescription= weatherCur.getJSONArray("weather").getJSONObject(0).getString("description")
            val weatherMain = weatherCur.getJSONArray("weather").getJSONObject(0).getString("main")
            val weatherIcon= weatherCur.getJSONArray("weather").getJSONObject(0).getString("icon")
            var weatherId= weatherCur.getJSONArray("weather").getJSONObject(0).getInt("id")
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}time","id",requireActivity().packageName))?.text=time
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}temp","id",requireActivity().packageName))?.text=temp
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_description","id",requireActivity().packageName))?.text=weatherDescription
            view?.findViewById<TextView>(resources.getIdentifier("${layoutId}weather_icon","id",requireActivity().packageName))?.text=weatherIcon
            Log.d("where",weatherDescription)
            Log.d("where",time)
            Log.d("where",weatherCur.getLong("dt").toString())

            val tempText = view?.findViewById<TextView>(R.id.temp_text)
            val mainweatherText = view?.findViewById<TextView>(R.id.mainweather_text)
            val weatherUpdateTime= view?.findViewById<TextView>(R.id.weather_update_time)
            tempText?.text=temp
            mainweatherText?.text=weatherMain

            weatherUpdateTime?.text= "Updated at : $time"

            var dayNight :Char = weatherIcon[2]

            Log.d("where","weatherid//100"+(weatherId/100).toString())

            //setting the Weather Icon
            if (weatherId/100 == 6){ // snow
                view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.snow)
            }else if (weatherId==800){
                if (dayNight=='d') {// clear day
                    view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.sun)
                    if (temp.toInt()>26){ //clear and hot
                        view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.sun_hot)
                    }
                }else{ //clear night
                    view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.moon)
                }
            }else if (weatherId/100 == 8){ // cloudy
                view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.cloud)
            }else if (weatherId/100==3 || weatherId/100==5){ //drizzle
                view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.rain)
            }else if (weatherId/100==2){ //thunderstorm
                view?.findViewById<ImageView>(R.id.weather_imageView)?.setImageResource(R.drawable.storm)
            }

            //setting the day or night
            if (dayNight=='n'){
                view?.findViewById<ConstraintLayout>(R.id.tab3)?.setBackgroundColor(ContextCompat.getColor(mContext!!,R.color.nightskyblue))
                tempText?.setTextColor(ContextCompat.getColor(mContext!!,R.color.nightyellow))
                mainweatherText?.setTextColor(ContextCompat.getColor(mContext!!,R.color.nightyellow))
                weatherUpdateTime?.setTextColor(ContextCompat.getColor(mContext!!,R.color.nightyellow))
                ramUsageTv.setTextColor(ContextCompat.getColor(mContext!!,R.color.nightyellow))
                ramUsageDescriptionTv.setTextColor(ContextCompat.getColor(mContext!!,R.color.nightyellow))
                btnV.setColorFilter(ContextCompat.getColor(mContext!!,R.color.nightyellow))
            }
        }


    }

}
