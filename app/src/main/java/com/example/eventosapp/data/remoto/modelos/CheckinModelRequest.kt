package com.example.eventosapp.data.remoto.modelos

import com.google.gson.annotations.SerializedName

class CheckinModelRequest(
    @SerializedName("eventId")
    val evento: Long,
    @SerializedName("name")
    val nome: String,
    @SerializedName("email")
    val email: String
)
