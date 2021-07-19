package com.prashant.weatherreportapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.prashant.weatherreportapp.database.models.ModelBookmark

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
@Dao
interface BookmarkDao {

    @Insert
    suspend fun insert(bookmark: ModelBookmark): Long

    @Query("SELECT * FROM Bookmark ORDER BY id DESC")
    suspend fun fetchAllBookmarks(): List<ModelBookmark>

    @Delete
    suspend fun delete(bookmarkedCity: ModelBookmark)
}