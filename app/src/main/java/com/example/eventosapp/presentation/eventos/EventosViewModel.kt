package com.example.eventosapp.presentation.eventos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventosapp.domain.models.EventosModelResposta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventosViewModel(
    private val useCase: BuscarEventosUseCase
) : ViewModel() {

    private var _eventos = MutableLiveData<Acao>()
    val eventos: LiveData<Acao> = _eventos

    fun buscarEventos() {
        _eventos.value = Acao.Carregando
        try {
            viewModelScope.launch {

                val evento = when (val resposta: BuscarEventosUseCase.Resposta =
                    withContext(Dispatchers.Default) { useCase.buscarEventos() }) {
                    is BuscarEventosUseCase.Resposta.Sucesso -> {
                        Acao.Sucesso(resposta.eventos)
                    }

                    is BuscarEventosUseCase.Resposta.Erro -> Acao.Erro(resposta.erro)
                }
                _eventos.value = evento
            }
        } catch (e: Exception) {
            _eventos.value = Acao.Erro(e)
        }
    }

    sealed class Acao {
        object Carregando : Acao()
        class Sucesso(val eventos: List<EventosModelResposta>) : Acao()

        class Erro(val erro: Exception) : Acao()
    }
}