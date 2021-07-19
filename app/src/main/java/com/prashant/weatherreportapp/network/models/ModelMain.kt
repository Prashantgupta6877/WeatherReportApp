package com.prashant.weatherreportapp.network.models

import com.google.gson.annotations.SerializedName

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
data class ModelMain(
    val temp: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("sea_level") val seaLevel: Int,
)
