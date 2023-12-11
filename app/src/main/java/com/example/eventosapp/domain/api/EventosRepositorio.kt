package com.example.eventosapp.domain.api

import com.example.eventosapp.domain.models.CheckinModelRequisicao
import com.example.eventosapp.domain.models.EventosModelResposta
import retrofit2.Response

interface EventosRepositorio {
    suspend fun fazerCheckin(body: CheckinModelRequisicao) : Response<Any>
    suspend fun buscarDetalhesEventos(id: Long) : Response<EventosModelResposta>

    suspend fun buscarEventos() : Response<List<EventosModelResposta>>
}