package com.alpaca.telalogin.ui.memes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.alpaca.telalogin.R
import com.alpaca.telalogin.databinding.FragmentMemeLegendadoBinding
import com.alpaca.telalogin.ui.memes.viewmodel.MemesViewModel

class MemeLegendadoFragment : Fragment() {
    private lateinit var binding: FragmentMemeLegendadoBinding
    private val viewModel: MemesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeLegendadoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.memeLegendadoImageView.load(viewModel.memeLegendado.value?.url)
        binding.favoritarMaterialCardView.setOnClickListener {
            with(binding.favoritarMaterialTextView){
                setCompoundDrawables(
                    null,
                    null,
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_estrela),
                    null
                )
                text = getString(R.string.texto_botao_desfavoritar)
            }
//            viewModel.salvarMemeLegendado()
        }
        binding.concluidoMaterialButton.setOnClickListener { requireActivity().onBackPressed() }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.limparReferenciaUltimoMemeLegendado()
            findNavController().popBackStack()
        }
    }
}