package com.hkyriacou.mobile_project2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hkyriacou.mobile_project2.api.WeatherItem
import com.hkyriacou.mobile_project2.api.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "WeatherFetchr"

class WeatherFetchr {

    private val weatherApi: WeatherApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)

    }

    fun fetchWorcesterWeather(): LiveData<WeatherItem> {
        val responseLiveData: MutableLiveData<WeatherItem> = MutableLiveData()
        val weatherRequest: Call<WeatherResponse> = weatherApi.fetchWorcesterWeather()
        weatherRequest.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch weather", t)
            }
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ){
                Log.d(TAG, "Response received")
                val weatherResponse: WeatherResponse? = response.body()
                val item: WeatherItem? = weatherResponse?.main
                responseLiveData.value = item
            }
        })
        return responseLiveData
    }

    fun fetchCoordsWeather(lat : Int, lon : Int): LiveData<WeatherItem> {
        val responseLiveData: MutableLiveData<WeatherItem> = MutableLiveData()
        val weatherRequest: Call<WeatherResponse> = weatherApi.fetchCoordsWeather(lat, lon)
        weatherRequest.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch weather", t)
            }
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ){
                Log.d(TAG, "Response received")
                val weatherResponse: WeatherResponse? = response.body()
                val item: WeatherItem? = weatherResponse?.main
                responseLiveData.value = item
            }
        })
        return responseLiveData
    }

}