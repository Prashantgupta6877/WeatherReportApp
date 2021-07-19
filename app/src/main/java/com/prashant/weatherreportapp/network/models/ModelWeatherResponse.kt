package com.prashant.weatherreportapp.network.models

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
data class ModelWeatherResponse(
    val name: String,
    val main: ModelMain,
    val wind: ModelWind
)
