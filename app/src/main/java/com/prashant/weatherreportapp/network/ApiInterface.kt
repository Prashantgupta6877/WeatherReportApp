package com.prashant.weatherreportapp.network

import com.prashant.weatherreportapp.network.models.ModelWeatherResponse
import com.prashant.weatherreportapp.network.ApiConstants.WEATHER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
interface ApiInterface {
    @GET(WEATHER)
    suspend fun getWeatherForecast(
        @Query("units") units: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): Response<ModelWeatherResponse>
}
