package com.prashant.weatherreportapp.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.prashant.weatherreportapp.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}