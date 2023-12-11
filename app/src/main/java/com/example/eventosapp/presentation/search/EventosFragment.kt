package com.example.eventosapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventosapp.databinding.FragmentEventosListBinding
import com.example.eventosapp.presentation.search.adapter.EventosViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class EventosFragment : Fragment() {

    private var _binding: FragmentEventosListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EventosViewAdapter

    private val viewModel: EventosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                modelo.id?.let { acessarDetalhes(it) }
            }
        })
    }

    private fun acessarDetalhes(id: Long) {
        val action = EventosFragmentDirections
            .actionListEventosFragmentToDetalhesEventosFragment(id)
        findNavController().navigate(action)
    }


    private fun observarEventos() {
        viewModel.buscarEventos()
        viewModel.eventos.observe(viewLifecycleOwner) { acao ->
            when (acao) {
                is EventosViewModel.Acao.Sucesso -> {
                    adapter.addItem(acao.eventos)
                }

                is EventosViewModel.Acao.Erro -> {
                    Toast.makeText(
                        requireContext(),
                        "Algo deu errado ao buscar eventos",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                is EventosViewModel.Acao.Carregando -> {
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