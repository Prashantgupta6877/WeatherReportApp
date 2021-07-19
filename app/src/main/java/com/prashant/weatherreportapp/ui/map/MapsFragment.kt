package com.prashant.weatherreportapp.ui.map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.prashant.weatherreportapp.R
import com.prashant.weatherreportapp.database.models.ModelBookmark
import com.prashant.weatherreportapp.databinding.FragmentMapsBinding
import com.prashant.weatherreportapp.ui.home.HomeViewModel
import com.prashant.weatherreportapp.utils.checkInternetConnection
import com.prashant.weatherreportapp.utils.firstLetterCapitalize
import java.util.*

private const val DEFAULT_LAT = 13.6832
private const val DEFAULT_LONG = 79.347

class MapsFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMapClickListener {
    private lateinit var googleMap: GoogleMap
    private var marker: Marker? = null
    private val markerOptions: MarkerOptions by lazy { MarkerOptions() }
    private var latLong: LatLng? = LatLng(DEFAULT_LAT, DEFAULT_LONG)
    private lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        addMap()
        initialize()
        clickListeners()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener(this)
        latLong?.let {
            addMarker(it)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }

    override fun onMapClick(p0: LatLng) {
        addMarker(p0)
        latLong = p0
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun addMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun addMarker(latLng: LatLng?) {
        marker?.remove()
        latLng?.let {
            markerOptions.position(it)
            marker = googleMap.addMarker(markerOptions)
        }
    }

    private fun clickListeners() {
        binding.btnAddBookMark.setOnClickListener {
            if (::googleMap.isInitialized) {
                val addresses: List<Address> =
                    latLong?.let {
                        Geocoder(context, Locale.getDefault()).run {
                            this.getFromLocation(
                                it.latitude,
                                it.longitude,
                                1
                            )
                        }
                    } as List<Address>
                try {
                    if (addresses.isNotEmpty() && addresses[0].locality != null) {

                        if (activity?.checkInternetConnection() == true) {
                            if (::viewModel.isInitialized) {
                                latLong?.let { latLng ->
                                    activity?.let {
                                        viewModel.insertBookmark(
                                            it,
                                            ModelBookmark(
                                                addresses[0].locality,
                                                latLng.latitude.toString(),
                                                latLng.longitude.toString()
                                            )
                                        )
                                        activity?.onBackPressed()
                                    }
                                }
                            }
                            showSnackBar(
                                addresses[0].locality.firstLetterCapitalize()
                                    .plus(" ")
                                    .plus(activity?.getString(R.string.added_into_bookmark))
                            )
                        } else {
                            showSnackBar(activity?.getString(R.string.internet_error) ?: "")
                        }
                    } else {
                        showSnackBar(activity?.getString(R.string.no_location_found_on_map) ?: "")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            binding.root,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
