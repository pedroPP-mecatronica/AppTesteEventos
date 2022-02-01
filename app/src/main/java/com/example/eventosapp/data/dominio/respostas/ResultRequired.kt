package com.example.eventosapp.data.dominio.respostas

sealed class ResultRequired<out T> {
    data class Success<out T>(val modelo: T): ResultRequired<T>()
    data class Error(val throwable: Throwable): ResultRequired<Nothing>()
    class Warning(val aviso: String): ResultRequired<Nothing>()
}