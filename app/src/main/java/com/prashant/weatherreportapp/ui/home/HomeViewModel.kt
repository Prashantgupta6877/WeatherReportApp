package com.prashant.weatherreportapp.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.prashant.weatherreportapp.database.models.ModelBookmark
import com.prashant.weatherreportapp.repositories.db.WeatherDbRepository
import com.prashant.weatherreportapp.utils.State

class HomeViewModel : ViewModel() {

    private val dbRepository = WeatherDbRepository()

    val dbState: LiveData<State>
        get() = dbRepository.state


    private var bookmarks: ArrayList<ModelBookmark>? = null

    fun insertBookmark(context: Context, modelBookmark: ModelBookmark) {
        dbRepository.insertBookMark(context, modelBookmark)
    }

    fun fetchAllBookmarks(context: Context): List<ModelBookmark> {
        bookmarks = dbRepository.fetchAllBookmarks(context)
        return bookmarks as List<ModelBookmark>
    }

    fun deleteBookmark(context: Context, modelBookmark: ModelBookmark) {
        bookmarks?.remove(modelBookmark)
        dbRepository.deleteBookmark(context, modelBookmark)
    }
}
