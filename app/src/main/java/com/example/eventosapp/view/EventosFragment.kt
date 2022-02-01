package com.example.eventosapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import com.example.eventosapp.data.dominio.respostas.ViewStates
import com.example.eventosapp.databinding.FragmentEventosListBinding
import com.example.eventosapp.viewmodel.EventosViewModel


class EventosFragment : Fragment() {

    private var _binding: FragmentEventosListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EventosViewAdapter
    private lateinit var eventosViewModel: EventosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventosViewModel = ViewModelProvider(this).get(EventosViewModel::class.java)
        _binding = FragmentEventosListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAdapter()
        observarEventos()
        observarClick()
    }

    private fun configAdapter() {
        adapter = EventosViewAdapter(mutableListOf())
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun observarClick() {
        adapter.itemClickListener(object : EventosViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val modelo = adapter.eventos[position]
                acessarDetalhes(modelo.id)
            }
        })
    }

    private fun acessarDetalhes(id: Long) {
        val action = EventosFragmentDirections
            .actionListEventosFragmentToDetalhesEventosFragment(id)
        findNavController().navigate(action)
    }


    fun observarEventos() {
        eventosViewModel.buscarEventos()
        eventosViewModel.eventos.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewStates.Sucesso<*> -> {
                    val eventos = state.list as List<EventosModelResponse>
                    adapter.addItem(eventos)
                }
                is ViewStates.Error -> {
                    Toast.makeText(requireContext(), state.erro.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is ViewStates.Aviso -> {
                    Toast.makeText(requireContext(), state.aviso, Toast.LENGTH_SHORT).show()
                }
                is ViewStates.Carregando -> {
                    Toast.makeText(requireContext(), "Carregando", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}