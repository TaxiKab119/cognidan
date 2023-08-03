package com.example.dancognitionapp.participants.data

import androidx.annotation.StringRes

data class Participant(
    val id: Int,
    val name: String
)

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