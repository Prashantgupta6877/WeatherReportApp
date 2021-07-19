package com.prashant.weatherreportapp.repositories.db

import android.content.Context
import com.prashant.weatherreportapp.database.BaseDatabase
import com.prashant.weatherreportapp.database.models.ModelBookmark
import com.prashant.weatherreportapp.repositories.BaseRepository
import com.prashant.weatherreportapp.utils.State
import kotlinx.coroutines.launch

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
class WeatherDbRepository : BaseRepository() {

    fun insertBookMark(context: Context, modelBookmark: ModelBookmark) {
        launch {
            BaseDatabase(context).getBookmarksDao().insert(modelBookmark)
            state.postValue(State.DONE)
        }
    }

    fun fetchAllBookmarks(context: Context): ArrayList<ModelBookmark> {
        val bookmarks = ArrayList<ModelBookmark>()
        updateState(State.LOADING)
        launch {
            bookmarks.addAll(BaseDatabase(context).getBookmarksDao().fetchAllBookmarks())
            state.postValue(State.DONE)
        }
        return bookmarks
    }

    fun deleteBookmark(context: Context, modelBookmark: ModelBookmark) {
        launch {
            BaseDatabase(context).getBookmarksDao().delete(modelBookmark)
            state.postValue(State.DONE)
        }
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}
