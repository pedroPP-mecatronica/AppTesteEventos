package com.example.eventosapp.presentation.detalhes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.eventosapp.R
import com.example.eventosapp.databinding.FragmentDetalhesEventosBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetalhesEventosFragment : Fragment() {

    private val args: DetalhesEventosFragmentArgs by navArgs()

    private val eventosViewModel: DetalhesEventosViewModel by viewModel()

    private var _binding: FragmentDetalhesEventosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalhesEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observarDetalhes()

        view.findViewById<FloatingActionButton>(R.id.checkin_detalhes).setOnClickListener {
            fazerCheckin(args.idEvento)
        }
    }

    private fun fazerCheckin(idEvento: Long) {
        val action =
            DetalhesEventosFragmentDirections.actionDetalhesEventosFragmentToCheckinEventosFragment(
                idEvento
            )
        findNavController().navigate(action)
    }

    private fun observarDetalhes() {
        eventosViewModel.buscarDetalhesEventos(args.idEvento)
        eventosViewModel.acao.observe(viewLifecycleOwner) { acao ->
            when (acao) {
                is DetalhesEventosViewModel.Acao.Sucesso -> {
                    val detalhes = acao.detalhes
                    binding.tituloDetalhes.loadData(detalhes.titulo.orEmpty(), null, null)
                    binding.descricaoDetalhesText.text = detalhes.descricao
                    Glide.with(this)
                        .load(detalhes.imagem)
                        .into(binding.imagemDetalhes)
                    binding.precoDetalhes.text = " Preço: " + detalhes.preco.toString() + "R$"
                }

                is DetalhesEventosViewModel.Acao.Erro -> {
                    Toast.makeText(requireContext(), acao.erro.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                is DetalhesEventosViewModel.Acao.Carregando -> {
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