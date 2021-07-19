package com.prashant.weatherreportapp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prashant.weatherreportapp.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private lateinit var helpViewModel: HelpViewModel
    private var _binding: FragmentHelpBinding? = null

    private val body = "<html>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<b>1. Bookmark Location</b> <br>\n" +
            "To add bookmark location click on + button. From the given map, select location of your interest to bookmark. <br><br>\n" +
            "\n" +
            "<b>2. Delete Location </b><br>\n" +
            "To delete the location from the bookmark list, tap on the delete icon. <br><br>"+
            "\n" +
            "<b>3. Check Weather Details </b><br>\n" +
            "Tap on any of the bookmarked location, it will open new screen where you can see the weather details like temp,min-max temp, wind, pressure etc. <br>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "</html>"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        helpViewModel =
            ViewModelProvider(this).get(HelpViewModel::class.java)

        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        val root: View? = binding?.root
        binding?.webView?.loadData(body,"text/html",null)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}