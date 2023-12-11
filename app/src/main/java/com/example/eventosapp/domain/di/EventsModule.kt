package com.example.eventosapp.domain.di

import com.example.eventosapp.domain.api.EventosRepositorio
import com.example.eventosapp.domain.api.EventosRepositorioImpl
import com.example.eventosapp.domain.api.IEventosRepositorioServico
import com.example.eventosapp.domain.api.RetrofitClient
import com.example.eventosapp.domain.usecases.EventosCheckinUseCase
import com.example.eventosapp.domain.usecases.BuscarDetalhesEventoUseCase
import com.example.eventosapp.domain.usecases.BuscarEventosUseCase
import com.example.eventosapp.presentation.checkin.CheckinEventosViewModel
import com.example.eventosapp.presentation.details.DetalhesEventosViewModel
import com.example.eventosapp.presentation.search.EventosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun loadEventsModules() = loadKoinModules(
    listOf(
        viewModelModule,
        repositoryModule,
        useCaseModule,
        apiModule
    )
)

private val repositoryModule = module {
    single {
        RetrofitClient.getInstance(IEventosRepositorioServico::class.java)
    }
}

private val apiModule = module {
    single<EventosRepositorio> { EventosRepositorioImpl(api = get()) }
}

private val useCaseModule = module(override = true) {
    factory { BuscarDetalhesEventoUseCase(get()) }
    factory { BuscarEventosUseCase(get()) }
    factory { EventosCheckinUseCase(get()) }
}


private val viewModelModule = module {

    viewModel {
        DetalhesEventosViewModel(get())
    }

    viewModel {
        EventosViewModel(get())
    }

    viewModel {
        CheckinEventosViewModel(get())
    }
}

