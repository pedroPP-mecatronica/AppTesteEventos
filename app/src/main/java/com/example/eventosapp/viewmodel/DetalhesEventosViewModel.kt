package com.example.eventosapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventosapp.data.dominio.respostas.ResultUseCase
import com.example.eventosapp.data.dominio.respostas.ViewStates
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import com.example.eventosapp.data.remoto.repositorio.EventosRepositorioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetalhesEventosViewModel : ViewModel() {

    private val useCase: EventosRepositorioUseCase = EventosRepositorioUseCase()

    private var _detalhes = MutableLiveData<ViewStates>()
    val detalhes: LiveData<ViewStates> = _detalhes

    fun buscarDetalhesEventos(id: Long) {
        try {
            viewModelScope.launch {
                _detalhes.value = ViewStates.Carregando

                val response = withContext(Dispatchers.Default) { useCase.buscarDetalhes(id) }
                val stateView = when (response) {
                    is ResultUseCase.Model<*> -> ViewStates.Sucesso(response.modelo as EventosModelResponse)
                    is ResultUseCase.Aviso -> ViewStates.Aviso(response.aviso)
                    is ResultUseCase.Error -> ViewStates.Error(response.erro)
                }
                _detalhes.value = stateView
            }
        } catch (exp: Exception) {
            _detalhes.value = ViewStates.Error(exp)
        }
    }
}