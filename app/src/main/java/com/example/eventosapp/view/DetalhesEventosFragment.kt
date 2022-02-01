package com.example.eventosapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import com.example.eventosapp.data.dominio.respostas.ViewStates
import com.example.eventosapp.databinding.FragmentDetalhesEventosBinding
import com.example.eventosapp.viewmodel.DetalhesEventosViewModel
import com.example.eventosapp.viewmodel.EventosViewModel
import kotlinx.android.synthetic.main.fragment_detalhes_eventos.*
import android.R
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.Target
import java.text.SimpleDateFormat
import java.util.*


class DetalhesEventosFragment : Fragment() {

    private val args: DetalhesEventosFragmentArgs by navArgs()

    private lateinit var eventosViewModel: DetalhesEventosViewModel

    private var _binding: FragmentDetalhesEventosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventosViewModel = ViewModelProvider(this).get(DetalhesEventosViewModel::class.java)
        _binding = FragmentDetalhesEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observarDetalhes()

        checkin_detalhes.setOnClickListener {
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
        eventosViewModel.detalhes.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewStates.Sucesso<*> -> {
                    val detalhes = state.list as EventosModelResponse
                    binding.tituloDetalhes.text = detalhes.titulo
                    binding.descricaoDetalhesText.text = detalhes.descricao
                    Glide.with(this)
                        .load(detalhes.imagem)
                        .into(binding.imagemDetalhes)
                    binding.precoDetalhes.text = " Preço: " + detalhes.preco.toString()+ "R$"
                }
                is ViewStates.Error -> {
                    Toast.makeText(requireContext(), state.erro.toString(), Toast.LENGTH_SHORT)
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