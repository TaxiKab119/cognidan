package com.example.dancognitionapp.participants.data

import android.os.Parcel
import android.os.Parcelable

data class Participant(
    val id: Int,
    val name: String
)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Participant> {
        override fun createFromParcel(parcel: Parcel): Participant {
            return Participant(parcel)
        }

        override fun newArray(size: Int): Array<Participant?> {
            return arrayOfNulls(size)
        }
    }
}

val participants = listOf<Participant>(
    Participant(1, "Alex Balan"),
    Participant(2, "Santa Claus"),
    Participant(3, "Grant Dong"),
    Participant(4, "Frauke Tillmans"),
    Participant(5, "King Kong"),
    Participant(6, "Rhiannon Brenner"),
    Participant(7, "Ana Rumpl"),
    Participant(8, "John Doe"),
    Participant(9, "Jacques Cousteau")
)