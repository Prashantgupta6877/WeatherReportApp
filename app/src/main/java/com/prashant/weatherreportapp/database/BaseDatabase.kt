package com.prashant.weatherreportapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prashant.weatherreportapp.database.daos.BookmarkDao
import com.prashant.weatherreportapp.database.models.ModelBookmark

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */

@Database(
    entities = [ModelBookmark::class],
    version = 1
)
abstract class BaseDatabase : RoomDatabase() {
    abstract fun getBookmarksDao(): BookmarkDao

    companion object {
        @Volatile
        private var instance: BaseDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BaseDatabase::class.java,
                "WeatherDatabase"
            ).build()
    }
}
