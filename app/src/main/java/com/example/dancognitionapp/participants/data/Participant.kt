package com.example.dancognitionapp.participants.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Participant(
    val id: String,
    val name: String,
    val notes: String = ""
)  : Parcelable {
    val internalId: Int get() = hashCode()
}