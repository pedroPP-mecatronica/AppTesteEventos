package com.example.eventosapp.data.remoto.modelos

import com.google.gson.annotations.SerializedName

class EventosModelResponse (
    @SerializedName("people")
    val pessoas:ArrayList<Any>,
    @SerializedName("date")
    val dados:Any,
    @SerializedName("description")
    val descricao:String,
    @SerializedName("image")
    val imagem:String,
    @SerializedName("longitude")
    val longitude: Float,
    @SerializedName("latitude")
    val latitude:Float,
    @SerializedName("price")
    val preco:Double,
    @SerializedName("title")
    val titulo:String,
    @SerializedName("id")
    val id: Long
)