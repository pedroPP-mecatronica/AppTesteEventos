package com.example.eventosapp.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckinModelRequisicao(
    @SerializedName("eventId")
    val evento: Long,
    @SerializedName("name")
    val nome: String,
    @SerializedName("email")
    val email: String
): Parcelable
