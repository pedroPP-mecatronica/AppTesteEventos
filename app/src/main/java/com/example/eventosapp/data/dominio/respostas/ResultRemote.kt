package com.example.eventosapp.data.dominio.respostas

import retrofit2.HttpException

sealed class ResultRemote<out T>{
    data class Success<out T>(val response: T) : ResultRemote<T>()
    class Warning(val aviso: String): ResultRemote<Nothing>()

    sealed class ErrorResponse(open val throwable: Throwable) : ResultRemote<Nothing>() {
        data class Unknown(override val throwable: Throwable) : ErrorResponse(throwable)
        data class Warning(override val throwable: HttpException) : ErrorResponse(throwable)
    }
}
