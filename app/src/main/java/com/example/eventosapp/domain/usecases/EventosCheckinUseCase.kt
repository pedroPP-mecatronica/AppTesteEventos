package com.example.eventosapp.domain.usecases

import com.example.eventosapp.domain.api.EventosRepositorio
import com.example.eventosapp.domain.models.CheckinModelRequisicao
import java.lang.Exception

class EventosCheckinUseCase(
    private val repositorio: EventosRepositorio
) {
    suspend fun fazerCheckin(id: Long, email: String, nome: String): Response {
        return repositorio.fazerCheckin(
            CheckinModelRequisicao(
                evento = id,
                email = email,
                nome = nome
            )
        ).takeIf { it.isSuccessful }?.let {
            Response.Sucesso
        } ?: Response.Erro(Exception("Não foi possível fazer checkin"))

    }

    sealed class Response {
       object Sucesso: Response()
       class Erro(val erro: Throwable): Response()
    }
}