package com.example.eventosapp.data.dominio.services

import com.example.eventosapp.data.dominio.respostas.ResultRemote
import com.example.eventosapp.data.remoto.modelos.CheckinModelRequest
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import retrofit2.Response

import retrofit2.http.*

interface EventosService {
    @POST("api/checkin")
    suspend fun fazerCheckin(
        @Body modelo: CheckinModelRequest
    ): Response<Any>

    @GET("api/events/{id}")
    suspend fun buscarDetalhesEventos(
        @Path("id") id: Long
    ): Response<EventosModelResponse>
    @GET("api/events")
    suspend fun buscarEventos(
    ): Response<List<EventosModelResponse>>
}