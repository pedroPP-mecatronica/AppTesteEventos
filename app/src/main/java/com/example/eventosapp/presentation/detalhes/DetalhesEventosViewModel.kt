package com.example.eventosapp.presentation.detalhes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventosapp.domain.models.EventosModelResposta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalhesEventosViewModel(
    private val useCase: BuscarDetalhesEventoUseCase
) : ViewModel() {

    private var _acao = MutableLiveData<Acao>()
    val acao: LiveData<Acao> = _acao

    fun buscarDetalhesEventos(id: Long) {
        try {
            viewModelScope.launch {
                _acao.value = Acao.Carregando

                val stateView = when (val resposta =
                    withContext(Dispatchers.Default) { useCase.buscarDetalhesEventos(id) }) {
                    is BuscarDetalhesEventoUseCase.Resposta.Sucesso -> Acao.Sucesso(resposta.detalhes)
                    is BuscarDetalhesEventoUseCase.Resposta.Erro -> throw Exception(resposta.erro)
                }
                _acao.value = stateView
            }
        } catch (e: Exception) {
            _acao.value = Acao.Erro(e)
        }
    }

    sealed class Acao {
        object Carregando : Acao()
        class Sucesso(val detalhes: EventosModelResposta) : Acao()

        class Erro(val erro: Exception) : Acao()
    }
}