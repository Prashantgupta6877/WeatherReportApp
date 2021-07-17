package com.prashant.weatherreportapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.prashant.weatherreportapp.R
import com.prashant.weatherreportapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        clickListeners()

        return root
    }

    private fun clickListeners() {
        binding.fabAdd.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homeToMap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
