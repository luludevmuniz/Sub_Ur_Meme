package com.alpaca.telalogin.ui.memes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alpaca.telalogin.R
import com.alpaca.telalogin.databinding.FragmentMemesFavoritosBinding
import com.alpaca.telalogin.extensions.navigateWithAnimations
import com.alpaca.telalogin.model.MemeLegendado
import com.alpaca.telalogin.ui.memes.adapter.ItemMemeAdapter
import com.alpaca.telalogin.ui.memes.adapter.ItemMemeFavoritoAdapter
import com.alpaca.telalogin.ui.memes.viewmodel.MemesViewModel

class MemesFavoritosFragment : Fragment(), ItemMemeFavoritoAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMemesFavoritosBinding
    private lateinit var adapterMeme: ItemMemeFavoritoAdapter
    private val viewModel: MemesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemesFavoritosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.buscarListaDeMemesLegendados()
        adapterMeme = ItemMemeFavoritoAdapter(emptyList(), this)
        binding.memesFavoritosRecyclerView.adapter = adapterMeme
        viewModel.listaDeMemesLegendados.observe(viewLifecycleOwner) {
            adapterMeme.atualizaLista(it)
        }
    }

    override fun onItemClick(memeLegendado: MemeLegendado) {
        viewModel.atualizarMemeLegendado(memeLegendado)
        findNavController().navigateWithAnimations(R.id.action_memesFavoritosFragment_to_memeFavoritoFragment)
    }
}