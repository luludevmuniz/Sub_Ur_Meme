package com.alpaca.suburmeme.ui.memes.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.alpaca.suburmeme.R
import com.alpaca.suburmeme.databinding.FragmentMemesBinding
import com.alpaca.suburmeme.extensions.navigateWithAnimations
import com.alpaca.suburmeme.ui.memes.adapter.ItemMemeAdapter
import com.alpaca.suburmeme.ui.memes.adapter.ItemMemeAdapter.OnItemClickListener
import com.alpaca.suburmeme.ui.memes.viewmodel.MemesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MemesFragment : Fragment(), OnItemClickListener, SearchView.OnQueryTextListener {

    private val viewModel: MemesViewModel by activityViewModels()
    private lateinit var binding: FragmentMemesBinding
    private lateinit var adapterMeme: ItemMemeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterMeme = ItemMemeAdapter(emptyList(), this)
        binding.memesRecyclerView.adapter = adapterMeme
        configuraMenuToolbar()
        viewModel.listaDeMemes.observe(viewLifecycleOwner) {
            adapterMeme.atualizaLista(it)
        }
    }

    private fun configuraMenuToolbar() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu_pesquisar, menu)
                val search = menu.findItem(R.id.itemMenuPesquisar)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@MemesFragment)
                menu.forEach {
                    it.icon.setTint(resources.getColor(R.color.white, activity?.theme))
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    override fun onItemClick(id: Int, textoSuperior: String, textoInferior: String) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main)
            {
                binding.progress.visibility = View.VISIBLE
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }

            val memeLegendado = viewModel.legendarMeme(id, textoSuperior, textoInferior)

            withContext(Dispatchers.Main) {
                if (memeLegendado != null) {
                    val action =
                        MemesFragmentDirections.actionMemesFragmentToMemeLegendadoFragment(
                            memeLegendado
                        )
                    findNavController().navigateWithAnimations(action.actionId, action.arguments)
                }
                binding.progress.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            procurarMemePeloNome(nome = query)
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            procurarMemePeloNome(nome = query)
        }
        return true
    }

    private fun procurarMemePeloNome(nome: String) {
        val query = "%$nome%"
        viewModel.procurarMemePeloNome(query).observe(viewLifecycleOwner) { listaFiltrada ->
            if (listaFiltrada.isNullOrEmpty()) {
                viewModel.listaDeMemes.value?.let { adapterMeme.atualizaLista(it) }
            }
            adapterMeme.atualizaLista(listaFiltrada)
        }
    }
}