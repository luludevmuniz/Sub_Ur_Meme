package com.alpaca.suburmeme.ui.memes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alpaca.suburmeme.databinding.FragmentMemesFavoritosBinding
import com.alpaca.suburmeme.extensions.navigateWithAnimations
import com.alpaca.suburmeme.model.MemeLegendado
import com.alpaca.suburmeme.ui.memes.adapter.ItemMemeFavoritoAdapter
import com.alpaca.suburmeme.ui.memes.viewmodel.MemesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        adapterMeme = ItemMemeFavoritoAdapter(emptyList(), this)
        viewModel.buscarListaDeMemesLegendados()
        binding.memesFavoritosRecyclerView.adapter = adapterMeme
        viewModel.listaDeMemesLegendados.observe(viewLifecycleOwner) {
            adapterMeme.atualizaLista(it)
        }
    }

    override fun onItemClick(memeLegendado: MemeLegendado) {
        val action =
            MemesFavoritosFragmentDirections.actionMemesFavoritosFragmentToMemeFavoritoFragment(
                memeLegendado
            )
        findNavController().navigateWithAnimations(action.actionId, action.arguments)
    }
}