package com.hkyriacou.mobile_project2

import com.hkyriacou.mobile_project2.api.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {


    @GET("/data/2.5/weather?q=worcester&appid=ed86934b3e914a7826d2499f14774795")
    fun fetchWorcesterWeather(): Call<WeatherResponse>

    @GET("/data/2.5/weather?appid=ed86934b3e914a7826d2499f14774795")
    fun fetchCoordsWeather(@Query("lat") lat  : Int, @Query("lon")lon : Int): Call<WeatherResponse>

}