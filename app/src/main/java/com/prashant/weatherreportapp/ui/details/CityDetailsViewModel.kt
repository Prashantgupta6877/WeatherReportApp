package com.prashant.weatherreportapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.prashant.weatherreportapp.database.models.ModelBookmark
import com.prashant.weatherreportapp.network.ApiConstants
import com.prashant.weatherreportapp.network.models.ModelWeatherResponse
import com.prashant.weatherreportapp.repositories.api.WeatherApiRepository
import com.prashant.weatherreportapp.utils.State

class CityDetailsViewModel : ViewModel() {
    private val apiRepository = WeatherApiRepository()

    val apiState: LiveData<State>
        get() = apiRepository.state

    val cityWeatherDetails: LiveData<ModelWeatherResponse>
        get() = apiRepository.weatherResponse

    fun getWeatherForecast(modelBookmark: ModelBookmark) {
        apiRepository.getWeatherForeCast(
            lat = modelBookmark.lat,
            lon = modelBookmark.lon,
            units = ApiConstants.METRIC
        )
    }
}
