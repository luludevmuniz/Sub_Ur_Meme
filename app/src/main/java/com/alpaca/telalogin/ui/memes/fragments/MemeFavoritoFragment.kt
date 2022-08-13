package com.alpaca.telalogin.ui.memes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import coil.load
import com.alpaca.telalogin.R
import com.alpaca.telalogin.databinding.FragmentMemeFavoritoBinding
import com.alpaca.telalogin.ui.memes.viewmodel.MemesViewModel

class MemeFavoritoFragment : Fragment() {
    private lateinit var binding: FragmentMemeFavoritoBinding
    private val viewModel: MemesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeFavoritoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.memeLegendadoImageView.load(viewModel.memeLegendado.value?.url)

        binding.favoritarMaterialCardView.setOnClickListener {
            if (viewModel.isMemeFavorito)
            {
                with(binding.desfavoritarMaterialTextView){
                    setCompoundDrawables(
                        null,
                        null,
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_estrela),
                        null
                    )
                    text = getString(R.string.texto_botao_desfavoritar)
                }
            }
            else{

            }

//            viewModel.salvarMemeLegendado()
        }
        binding.concluidoMaterialButton.setOnClickListener { requireActivity().onBackPressed() }

    }
}