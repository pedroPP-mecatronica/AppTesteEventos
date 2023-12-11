package com.example.eventosapp.domain.api

import com.example.eventosapp.domain.models.CheckinModelRequisicao
import com.example.eventosapp.domain.models.EventosModelResposta
import retrofit2.Response

class EventosRepositorioImpl(
    private val api: IEventosRepositorioServico
) : EventosRepositorio {
    override suspend fun fazerCheckin(body: CheckinModelRequisicao): Response<Any> =
        api.fazerCheckin(body)

    override suspend fun buscarDetalhesEventos(id: Long): Response<EventosModelResposta> =
        api.buscarDetalhesEventos(id)

    override suspend fun buscarEventos(): Response<List<EventosModelResposta>> =
        api.buscarEventos()
}