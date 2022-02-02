package com.example.eventosapp.data.remoto.repositorio

import com.example.eventosapp.data.dominio.respostas.ResultRequired
import com.example.eventosapp.data.dominio.services.EventosService
import com.example.eventosapp.data.remoto.RetrofitClient
import com.example.eventosapp.data.remoto.modelos.CheckinModelRequest
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse

class EventosRepositorioRemoto {

    private val remote = RetrofitClient.createService(EventosService::class.java)

    suspend fun buscarEventos(): ResultRequired<List<EventosModelResponse>> {
        try {
            val result = remote.buscarEventos()
            return if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                ResultRequired.Success(result.body().orEmpty())
            } else if (result.body() == null) {
                ResultRequired.Warning(result.message().toString())
            } else {
                throw  Exception(result.message())
            }
        } catch (exp: Throwable) {
            return ResultRequired.Error(exp)
        }
    }


    suspend fun buscarDetalhes(id: Long): ResultRequired<EventosModelResponse> {
        try {
            val result = remote.buscarDetalhesEventos(id)
            return if (result.isSuccessful) {
                ResultRequired.Success(result.body()!!)
            } else {
                throw Exception(result.message())
            }
        } catch (exp: Throwable) {
            return ResultRequired.Error(exp)
        }
    }

    suspend fun fazerCheckin(id: Long, email: String, nome: String): ResultRequired<Any> {
        try {
            val usuario = CheckinModelRequest(id, email, nome)
            val result = remote.fazerCheckin(usuario)
            return if (result.isSuccessful) {
                ResultRequired.Success(result.body()!!)
            } else {
                throw Exception(result.message())
            }
        } catch (exp: Throwable) {
            return  ResultRequired.Error(exp)
        }
    }
}