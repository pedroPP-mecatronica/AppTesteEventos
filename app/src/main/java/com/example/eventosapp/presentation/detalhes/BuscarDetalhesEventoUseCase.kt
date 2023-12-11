package com.example.eventosapp.presentation.detalhes

import com.example.eventosapp.domain.api.EventosRepositorio
import com.example.eventosapp.domain.models.EventosModelResposta
import kotlin.Exception

class BuscarDetalhesEventoUseCase(
    private val repositorio: EventosRepositorio
) {
    suspend fun buscarDetalhesEventos(id: Long): Resposta {
        return repositorio.buscarDetalhesEventos(id)
            .takeIf { it.isSuccessful }?.let {
                it.body()?.let { resposta -> Resposta.Sucesso(resposta) }
            } ?: Resposta.Erro(Exception("Não foi possível buscar detalhes"))
    }

    sealed class Resposta {
        class Sucesso(val detalhes: EventosModelResposta) : Resposta()
        class Erro(val erro: Exception) : Resposta()
    }
}