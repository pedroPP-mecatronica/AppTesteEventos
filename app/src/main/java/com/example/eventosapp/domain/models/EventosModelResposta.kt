package com.example.eventosapp.domain.models

import com.google.gson.annotations.SerializedName

class EventosModelResposta(
    @SerializedName("people")
    val pessoas: ArrayList<Any>? = null,
    @SerializedName("date")
    val dados: Any? = null,
    @SerializedName("description")
    val descricao: String? = null,
    @SerializedName("image")
    val imagem: String? = null,
    @SerializedName("longitude")
    val longitude: Float? = null,
    @SerializedName("latitude")
    val latitude: Float? = null,
    @SerializedName("price")
    val preco: Double? = null,
    @SerializedName("title")
    val titulo: String? = null,
    @SerializedName("id")
    val id: Long? = null
)