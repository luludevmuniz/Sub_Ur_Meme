package com.alpaca.telalogin.ui.memes.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alpaca.telalogin.R
import com.alpaca.telalogin.databinding.FragmentMemeFavoritoBinding
import com.alpaca.telalogin.ui.MainActivity
import com.alpaca.telalogin.ui.memes.viewmodel.MemesViewModel
import com.google.android.material.snackbar.Snackbar

class MemeFavoritoFragment : Fragment() {
    private lateinit var binding: FragmentMemeFavoritoBinding
    private val viewModel: MemesViewModel by activityViewModels()
    private var uriImagem: Uri? = null
    private val args by navArgs<MemeFavoritoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeFavoritoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraMenuToolbar()
        binding.memeLegendadoImageView.setImageBitmap(args.meme.bitmap)
    }

    private fun configuraMenuToolbar() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu_meme_favorito, menu)
                menu.forEach {
                    it.icon.setTint(resources.getColor(R.color.white, activity?.theme))
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.itemMenuCompartilhar -> {
                        args.meme.bitmap?.let {
                            (requireActivity() as MainActivity).abreIntentCompartilhar(
                                it
                            )
                        }
                        true
                    }
                    R.id.itemMenuExcluir -> {
                        mostraAlertaExcluirMeme()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun mostraAlertaExcluirMeme() {
        AlertDialog
            .Builder(activity).setTitle("Atenção")
            .setMessage("Tem certeza de que deseja excluir o meme?")
            .setPositiveButton("Sim") { _, _ ->
                viewModel.excluirMemeLegendado(memeLegendado = args.meme)
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    "Meme excluído com sucesso!",
                    Snackbar.LENGTH_LONG
                ).show()
                findNavController().popBackStack()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    override fun onDetach() {
        super.onDetach()
        uriImagem?.let { requireActivity().contentResolver?.delete(it, null, null) }
    }
}