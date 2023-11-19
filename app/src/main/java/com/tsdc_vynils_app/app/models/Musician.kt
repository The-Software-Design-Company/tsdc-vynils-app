package com.tsdc_vynils_app.app.models

import java.util.Date

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*

data class Musician(
    val id: Int,
    val name: String,
    val description: String,
    val birthDate: Date,
    val image: String,
    val albums: List<Album>,
    val performerPrizes: List<Performer>,
    val imagenResId: Int
) : Serializable {

/*constructor(parcel: Parcel) : this(
    parcel.readInt(),
    parcel.readString() ?: "",
    parcel.readString() ?: "",
    Date(parcel.readLong()), // Convertir Long a Date
    parcel.readString() ?: "",
    emptyList(),
    emptyList(),
    parcel.readInt()
)

override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(id)
    parcel.writeString(name)
    parcel.writeString(description)
    parcel.writeLong(birthDate.time) // Convertir Date a Long
    parcel.writeString(image)
    // Si las listas no son nulas, puedes ajustar este código para escribir las listas
    parcel.writeList(albums)
    parcel.writeList(performerPrizes)
    parcel.writeInt(imagenResId)
}

override fun describeContents(): Int {
    return 0
}

companion object CREATOR : Parcelable.Creator<Musician> {
    override fun createFromParcel(parcel: Parcel): Musician {
        return Musician(parcel)
    }

    override fun newArray(size: Int): Array<Musician?> {
        return arrayOfNulls(size)
    }
}*/
}
