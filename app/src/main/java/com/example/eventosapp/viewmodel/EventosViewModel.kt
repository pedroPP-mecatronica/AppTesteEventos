package com.example.eventosapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventosapp.data.dominio.respostas.ResultUseCase
import com.example.eventosapp.data.dominio.respostas.ViewStates
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import com.example.eventosapp.data.remoto.repositorio.EventosRepositorioRemoto
import com.example.eventosapp.data.remoto.repositorio.EventosRepositorioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventosViewModel() : ViewModel() {

    private val useCase: EventosRepositorioUseCase = EventosRepositorioUseCase()

    private var _eventos = MutableLiveData<ViewStates>()
    val eventos: LiveData<ViewStates> = _eventos

    fun buscarEventos() {
        _eventos.value = ViewStates.Carregando
        try {
            viewModelScope.launch {

                val resposta:ResultUseCase = withContext(Dispatchers.Default) { useCase.buscarEventos() }

                val evento = when (resposta) {
                    is ResultUseCase.Model<*> -> {
                        ViewStates.Sucesso(resposta.modelo as List<EventosModelResponse>)
                    }
                    is ResultUseCase.Aviso -> ViewStates.Aviso(resposta.aviso)
                    is ResultUseCase.Error -> ViewStates.Error(resposta.erro)
                }
                _eventos.value = evento
            }
        } catch (exp: Exception) {
            _eventos.value = ViewStates.Error(exp)
        }
    }
}