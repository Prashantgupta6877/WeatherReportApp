package com.prashant.weatherreportapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.weatherreportapp.R
import com.prashant.weatherreportapp.adapters.BookmarkAdapter
import com.prashant.weatherreportapp.database.models.ModelBookmark
import com.prashant.weatherreportapp.databinding.FragmentHomeBinding
import com.prashant.weatherreportapp.ui.details.CITY
import com.prashant.weatherreportapp.utils.State
import com.prashant.weatherreportapp.utils.visibleInvisible

class HomeFragment : Fragment(), BookmarkAdapter.BookmarkItemClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeViewModel()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View? = binding?.root

        clickListeners()

        binding?.rvBookmarks?.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = BookmarkAdapter(
                homeViewModel.fetchAllBookmarks(this.requireContext()),
                this
            )
        }
        observers()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteClicked(bookmark: ModelBookmark) {
        context?.let {
            homeViewModel.deleteBookmark(it, bookmark)
        }
    }

    override fun onItemClicked(view: View, bookmark: ModelBookmark) {
        val bundle = Bundle()
        bundle.putParcelable(CITY, bookmark)
        Navigation.findNavController(view).navigate(R.id.homeToCityDetails, bundle)
    }

    private fun initializeViewModel() {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun clickListeners() {
        binding?.fabAdd?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homeToMap)
        }
    }

    private fun observers() {
        homeViewModel.dbState.observe(viewLifecycleOwner, { state ->
            when (state) {
                State.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                State.DONE -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.rvBookmarks?.adapter?.notifyDataSetChanged()
                    isListEmpty(binding?.rvBookmarks?.adapter?.itemCount == 0)
                }
                else -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        })
    }

    private fun isListEmpty(isEmpty: Boolean) {
        binding?.txtEmptyMsg?.visibility = isEmpty.visibleInvisible()
        binding?.rvBookmarks?.visibility = isEmpty.not().visibleInvisible()
    }
}

