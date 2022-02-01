package com.example.eventosapp.data.dominio.respostas

sealed class ResultUseCase {
    data class Model<T>(val modelo: T): ResultUseCase()
    data class Aviso(val aviso: String): ResultUseCase()
    data class Error(val erro: Throwable): ResultUseCase()
}
