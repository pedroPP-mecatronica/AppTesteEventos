package com.example.eventosapp.data.remoto.repositorio

import com.example.eventosapp.data.dominio.respostas.ResultRequired
import com.example.eventosapp.data.dominio.respostas.ResultUseCase

class EventosRepositorioUseCase() {

    val repositorioRemoto: EventosRepositorioRemoto = EventosRepositorioRemoto()
    suspend fun buscarEventos(): ResultUseCase =
        when (val resultado = repositorioRemoto.buscarEventos()) {
            is ResultRequired.Success -> {
                ResultUseCase.Model(resultado.modelo)
            }
            is ResultRequired.Warning -> {
                ResultUseCase.Aviso(resultado.aviso)
            }
            is ResultRequired.Error -> {
                ResultUseCase.Error(resultado.throwable)
            }
        }


    suspend fun buscarDetalhes(id: Long): ResultUseCase =
        when (val resultado = repositorioRemoto.buscarDetalhes(id)) {
            is ResultRequired.Success -> {
                ResultUseCase.Model(resultado.modelo)
            }
            is ResultRequired.Warning -> {
                ResultUseCase.Aviso(resultado.aviso)
            }
            is ResultRequired.Error -> {
                ResultUseCase.Error(resultado.throwable)
            }
        }


     suspend fun fazerCheckin(id: Long, email: String, nome: String): ResultUseCase =
        when (val resultado = repositorioRemoto.fazerCheckin(id,email,nome)) {
            is ResultRequired.Success -> {
                ResultUseCase.Model(resultado.modelo)
            }
            is ResultRequired.Warning -> {
                ResultUseCase.Aviso(resultado.aviso)
            }
            is ResultRequired.Error -> {
                ResultUseCase.Error(resultado.throwable)
            }
        }
}