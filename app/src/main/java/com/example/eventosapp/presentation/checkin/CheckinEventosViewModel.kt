package com.example.eventosapp.presentation.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventosapp.domain.usecases.EventosCheckinUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckinEventosViewModel(
    private val useCase: EventosCheckinUseCase
) : ViewModel() {

    private var _checkin = MutableLiveData<Acao>()
    val checkin: LiveData<Acao> = _checkin

    fun fazerCheckin(id: Long, nome: String, email: String) {
        try {
            viewModelScope.launch {
                _checkin.value = Acao.Carregando

                val response =
                    withContext(Dispatchers.Default) { useCase.fazerCheckin(id, nome, email) }
                val stateView = when (response) {
                    is EventosCheckinUseCase.Response.Sucesso -> Acao.Sucesso
                    is EventosCheckinUseCase.Response.Erro -> Acao.Erro(response.erro)
                }
                _checkin.value = stateView
            }
        } catch (e: Throwable) {
            _checkin.value = Acao.Erro(e)
        }
    }

    sealed class Acao {
        object Carregando: Acao()
        object Sucesso: Acao()

        class Erro(val erro: Throwable): Acao()
    }
}