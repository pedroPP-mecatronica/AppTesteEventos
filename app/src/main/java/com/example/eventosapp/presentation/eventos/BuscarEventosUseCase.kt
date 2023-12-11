package com.example.eventosapp.presentation.eventos

import com.example.eventosapp.domain.api.EventosRepositorio
import com.example.eventosapp.domain.models.EventosModelResposta
import kotlin.Exception

class BuscarEventosUseCase(
    private val repositorio: EventosRepositorio
) {
    suspend fun buscarEventos(): Resposta =
        repositorio.buscarEventos().takeIf { it.isSuccessful }?.let {
            Resposta.Sucesso(it.body().orEmpty())
        } ?: Resposta.Erro(Exception("Não foi possível buscar eventos"))

    sealed class Resposta {
        class Sucesso(val eventos: List<EventosModelResposta>) : Resposta()
        class Erro(val erro: Exception) : Resposta()
    }
}