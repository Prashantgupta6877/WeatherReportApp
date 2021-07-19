package com.prashant.weatherreportapp.repositories.api

import androidx.lifecycle.MutableLiveData
import com.prashant.weatherreportapp.network.ApiBuilder
import com.prashant.weatherreportapp.network.ApiConstants
import com.prashant.weatherreportapp.network.models.ModelWeatherResponse
import com.prashant.weatherreportapp.repositories.BaseRepository
import com.prashant.weatherreportapp.utils.State
import kotlinx.coroutines.launch

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
class WeatherApiRepository : BaseRepository() {

    var weatherResponse = MutableLiveData<ModelWeatherResponse>()

    fun getWeatherForeCast(
        units: String,
        lat: String,
        lon: String,
    ) {
        launch {
            updateState(State.LOADING)
            val response = ApiBuilder.createBuilder().getWeatherForecast(
                units = units,
                lat = lat,
                lon = lon,
                appid = ApiConstants.OPEN_WEATHER_MP_API_KEY
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    weatherResponse.postValue(response.body())
                }
                updateState(State.DONE)
            } else {
                updateState(State.ERROR)
            }
        }
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}
