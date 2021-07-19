package com.prashant.weatherreportapp.database.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
@Entity(tableName = "Bookmark")
data class ModelBookmark(
    val city: String,
    val lat: String,
    val lon: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(city)
        parcel.writeString(lat)
        parcel.writeString(lon)
        parcel.writeInt(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ModelBookmark> {
        override fun createFromParcel(parcel: Parcel): ModelBookmark {
            return ModelBookmark(parcel)
        }

        override fun newArray(size: Int): Array<ModelBookmark?> {
            return arrayOfNulls(size)
        }
    }
}
