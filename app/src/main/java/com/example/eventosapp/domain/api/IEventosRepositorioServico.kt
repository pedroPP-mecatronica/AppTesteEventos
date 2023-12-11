package com.example.eventosapp.domain.api

import com.example.eventosapp.domain.models.CheckinModelRequisicao
import com.example.eventosapp.domain.models.EventosModelResposta
import retrofit2.Response

import retrofit2.http.*

interface IEventosRepositorioServico {
    @POST("api/checkin")
    suspend fun fazerCheckin(
        @Body body: CheckinModelRequisicao
    ): Response<Any>

    @GET("api/events/{id}")
    suspend fun buscarDetalhesEventos(
        @Path("id") id: Long
    ): Response<EventosModelResposta>
    @GET("api/events")
    suspend fun buscarEventos(
    ): Response<List<EventosModelResposta>>
}