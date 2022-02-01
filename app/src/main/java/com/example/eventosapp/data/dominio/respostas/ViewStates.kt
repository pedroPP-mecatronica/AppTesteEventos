package com.example.eventosapp.data.dominio.respostas

sealed class ViewStates{
    object Carregando : ViewStates()
    class Sucesso<T>(val list: T) : ViewStates()
    class Aviso(val aviso: String) : ViewStates()
    class Error(val erro: Throwable) : ViewStates()
}
