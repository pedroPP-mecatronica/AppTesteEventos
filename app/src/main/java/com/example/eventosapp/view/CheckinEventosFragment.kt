package com.example.eventosapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eventosapp.R
import com.example.eventosapp.data.dominio.respostas.ViewStates
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import com.example.eventosapp.databinding.CheckinEventosFragmentBinding
import com.example.eventosapp.viewmodel.CheckinEventosViewModel
import com.example.eventosapp.viewmodel.EventosViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheckinEventosFragment : BottomSheetDialogFragment() {

    private val args: DetalhesEventosFragmentArgs by navArgs()

    private lateinit var eventosViewModel: CheckinEventosViewModel

    private var _binding: CheckinEventosFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventosViewModel = ViewModelProvider(this).get(CheckinEventosViewModel::class.java)
        _binding = CheckinEventosFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observarCheckin()
        binding.confirmar.setOnClickListener {
            val nome = binding.nomeCheckin.text.toString()
            val email = binding.emailCheckin.text.toString()
            eventosViewModel.fazerCheckin(args.idEvento, nome, email)
        }

        binding.cancelar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observarCheckin() {
        eventosViewModel.checkin.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewStates.Sucesso<*> -> {
                        Toast.makeText(requireContext(),"Checkin feito",Toast.LENGTH_SHORT).show()
                        this.dismiss()
                }
                is ViewStates.Error -> {
                    Toast.makeText(requireContext(), state.erro.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is ViewStates.Aviso -> {
                    Toast.makeText(requireContext(), state.aviso, Toast.LENGTH_SHORT).show()
                }
                is ViewStates.Carregando -> {
                    Toast.makeText(requireContext(), "Realizando Checkin", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}