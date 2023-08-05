package com.example.dancognitionapp.participants.data

import android.os.Parcel
import android.os.Parcelable

data class Participant(
    val id: String,
    val name: String,
    val notes: String = ""
)  : Parcelable {
    val internalId: Int get() = hashCode()
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(notes)
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
    Participant("ABC", "Alex Balan"),
    Participant("002", "Santa Claus"),
    Participant("121", "Grant Dong"),
    Participant("662", "Frauke Tillmans"),
    Participant("515", "King Kong"),
    Participant("800", "Rhiannon Brenner"),
    Participant("10", "Ana Rumpl"),
    Participant("177", "John Doe"),
    Participant("A16", "Jacques Cousteau")
)