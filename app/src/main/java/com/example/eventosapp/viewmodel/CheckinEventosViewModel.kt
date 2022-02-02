package com.example.eventosapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventosapp.data.dominio.respostas.ResultUseCase
import com.example.eventosapp.data.dominio.respostas.ViewStates
import com.example.eventosapp.data.remoto.repositorio.EventosRepositorioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CheckinEventosViewModel : ViewModel() {

    private val useCase: EventosRepositorioUseCase = EventosRepositorioUseCase()

    private var _checkin = MutableLiveData<ViewStates>()
    val checkin: LiveData<ViewStates> = _checkin

    fun fazerCheckin(id: Long, nome: String, email: String) {
        try {
            viewModelScope.launch {
                _checkin.value = ViewStates.Carregando

                val response =
                    withContext(Dispatchers.Default) { useCase.fazerCheckin(id, nome, email) }
                val stateView = when (response) {
                    is ResultUseCase.Model<*> -> ViewStates.Sucesso(response.modelo)
                    is ResultUseCase.Aviso -> ViewStates.Aviso(response.aviso)
                    is ResultUseCase.Error -> ViewStates.Error(response.erro)
                }
                _checkin.value = stateView
            }
        } catch (exp: Throwable) {
            _checkin.value = ViewStates.Error(exp)
        }
    }
}