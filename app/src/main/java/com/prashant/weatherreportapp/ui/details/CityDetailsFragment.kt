package com.prashant.weatherreportapp.ui.details

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.prashant.weatherreportapp.R
import com.prashant.weatherreportapp.database.models.ModelBookmark
import com.prashant.weatherreportapp.databinding.CityDetailsFragmentBinding
import com.prashant.weatherreportapp.utils.*

const val CITY = "city"

class CityDetailsFragment : Fragment() {

    private lateinit var viewModel: CityDetailsViewModel
    private var _binding: CityDetailsFragmentBinding? = null
    private var bookMark: ModelBookmark? = null
    private var isMetric: Boolean = true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CityDetailsFragmentBinding.inflate(inflater, container, false)
        initialize()
        bookMark = arguments?.getParcelable(CITY)

        activity?.let {
            if (it.checkInternetConnection()) {
                bookMark?.let {
                    viewModel.getWeatherForecast(it)
                }
            } else {
                Toast.makeText(it, it.getString(R.string.internet_error), Toast.LENGTH_SHORT).show()
            }
        }

        observers()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialize() {
        viewModel =
            ViewModelProvider(this).get(CityDetailsViewModel::class.java)

        PreferenceManager.getDefaultSharedPreferences(activity)?.let {
            isMetric = it.getBoolean("unit", true)
        }
    }

    private fun observers() {
        viewModel.apiState.observe(viewLifecycleOwner, { state ->
            binding?.progressBar?.visibility = (state == State.LOADING).visibleInvisible()
        })

        viewModel.cityWeatherDetails.observe(viewLifecycleOwner, { weatherDetails ->
            weatherDetails?.let { details ->
                binding?.let {
                    if (TextUtils.isEmpty(details.name).not()) {
                        it.txtCity.text = details.name
                    }

                    with(details.main) {
                        if (isMetric) {
                            it.txtTemp.text = getString(R.string.celsius, this.temp)
                            it.txtMaxTemp.text = getString(R.string.celsius, this.tempMax)
                            it.txtMinTemp.text = getString(R.string.celsius, this.tempMin)
                        } else {
                            it.txtTemp.text = getString(R.string.fahrenheit, this.temp.celToFah())
                            it.txtMaxTemp.text =
                                getString(R.string.fahrenheit, this.tempMax.celToFah())
                            it.txtMinTemp.text =
                                getString(R.string.fahrenheit, this.tempMin.celToFah())
                        }
                        it.txtHumidity.text = this.humidity.toString()
                        it.txtPressure.text = this.pressure.toString()
                        it.txtSea.text = this.seaLevel.toString()
                    }

                    with(details.wind) {
                        if (isMetric) {
                            it.txtWind.text = getString(R.string.km, this.speed)
                        } else {
                            it.txtWind.text = getString(R.string.miles, this.speed.kmToMiles())
                        }
                    }
                }
            }
        })
    }
}
