package com.hkyriacou.mobile_project2

import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {


    @GET("/data/2.5/weather?q=worcester&appid=ed86934b3e914a7826d2499f14774795")
    fun fetchWorcesterWeather(): Call<String>

}