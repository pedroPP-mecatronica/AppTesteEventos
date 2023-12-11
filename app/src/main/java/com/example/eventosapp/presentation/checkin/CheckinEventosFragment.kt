package com.example.eventosapp.presentation.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eventosapp.databinding.CheckinEventosFragmentBinding
import com.example.eventosapp.presentation.detalhes.DetalhesEventosFragmentArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckinEventosFragment : BottomSheetDialogFragment() {

    private val args: DetalhesEventosFragmentArgs by navArgs()

    private val viewModel: CheckinEventosViewModel by viewModel()

    private var _binding: CheckinEventosFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CheckinEventosFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observarCheckin()
        binding.confirmar.setOnClickListener {
            val nome = binding.nomeCheckin.text.toString()
            val email = binding.emailCheckin.text.toString()
            viewModel.fazerCheckin(args.idEvento, nome, email)
        }

        binding.cancelar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observarCheckin() {
        viewModel.checkin.observe(viewLifecycleOwner) { acao ->
            when (acao) {
                is CheckinEventosViewModel.Acao.Sucesso-> {
                        Toast.makeText(requireContext(),"Checkin feito",Toast.LENGTH_SHORT).show()
                        this.dismiss()
                }
                is CheckinEventosViewModel.Acao.Erro -> {
                    Toast.makeText(requireContext(), acao.erro.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is CheckinEventosViewModel.Acao.Carregando -> {
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