package com.alpaca.telalogin.ui.memes.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.alpaca.telalogin.R
import com.alpaca.telalogin.databinding.FragmentMemeLegendadoBinding
import com.alpaca.telalogin.model.MemeLegendado
import com.alpaca.telalogin.ui.MainActivity
import com.alpaca.telalogin.ui.memes.viewmodel.MemesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MemeLegendadoFragment : Fragment() {
    private lateinit var binding: FragmentMemeLegendadoBinding
    private val viewModel: MemesViewModel by activityViewModels()
    private val args by navArgs<MemeLegendadoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeLegendadoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carregaImagemMeme()

        binding.favoritarMaterialCardView.setOnClickListener {
            with(binding.favoritarMaterialTextView) {
                setCompoundDrawables(
                    null,
                    null,
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_estrela),
                    null
                )
            }
            viewModel.salvarMemeLegendado(memeLegendado = obtemNovoMemeLegendado())
            it.isEnabled = false
        }

        binding.compartilharMaterialCardView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                obtemBitmap()?.let { bitmap ->
                    (requireActivity() as MainActivity).abreIntentCompartilhar(bitmap)
                }
            }
        }

        binding.concluidoMaterialButton.setOnClickListener { requireActivity().onBackPressed() }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun carregaImagemMeme() {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = obtemBitmap()
            withContext(Dispatchers.Main) {
                binding.memeLegendadoImageView
                    .setImageBitmap(bitmap)
            }
        }
    }

    private suspend fun obtemBitmap(): Bitmap? {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(args.meme.url)
            .build()

        val result: Drawable = (loading.execute(request) as SuccessResult).drawable

        return (result as BitmapDrawable).bitmap
    }

    private fun obtemNovoMemeLegendado(): MemeLegendado {
        return MemeLegendado(
            id = args.meme.id,
            url = args.meme.url,
            page_url = args.meme.page_url,
            bitmap = (binding.memeLegendadoImageView.drawable as BitmapDrawable).bitmap
        )
    }
}