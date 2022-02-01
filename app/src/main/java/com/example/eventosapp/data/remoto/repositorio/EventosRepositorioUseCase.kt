package com.example.eventosapp.data.remoto.repositorio

import com.example.eventosapp.data.dominio.respostas.ResultRequired
import com.example.eventosapp.data.dominio.respostas.ResultUseCase
import java.lang.Exception

class EventosRepositorioUseCase() {

    val repositorioRemoto: EventosRepositorioRemoto = EventosRepositorioRemoto()

    suspend fun buscarEventos(): ResultUseCase =
        try {
            when (val resultado = repositorioRemoto.buscarEventos()) {
                is ResultRequired.Success -> {
                    ResultUseCase.Model(resultado.modelo)
                }
                is ResultRequired.Warning -> {
                    ResultUseCase.Aviso(resultado.aviso)
                }
                is ResultRequired.Error -> throw Exception()
            }
        } catch (exp: Exception) {
            ResultUseCase.Error(exp)
        }


    suspend fun buscarDetalhes(id: Long): ResultUseCase =
        try {
            when (val resultado = repositorioRemoto.buscarDetalhes(id)) {
                is ResultRequired.Success -> {
                    ResultUseCase.Model(resultado.modelo)
                }
                is ResultRequired.Warning -> {
                    ResultUseCase.Aviso(resultado.aviso)
                }
                is ResultRequired.Error -> throw Exception()
            }
        } catch (exp: Exception) {
            ResultUseCase.Error(exp)
        }


    suspend fun fazerCheckin(id: Long, email: String, nome: String): ResultUseCase =
        try {
            when (val resultado = repositorioRemoto.fazerCheckin(id, email, nome)) {
                is ResultRequired.Success -> {
                    ResultUseCase.Model(resultado.modelo)
                }
                is ResultRequired.Warning -> {
                    ResultUseCase.Aviso(resultado.aviso)
                }
                is ResultRequired.Error -> throw Exception()
            }
        } catch (exp: Exception) {
            ResultUseCase.Error(exp)
        }
}