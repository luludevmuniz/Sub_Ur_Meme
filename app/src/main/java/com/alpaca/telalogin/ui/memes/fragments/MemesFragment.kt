package com.alpaca.telalogin.ui.memes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alpaca.telalogin.databinding.FragmentMemesBinding
import com.alpaca.telalogin.ui.memes.adapter.ItemMemeAdapter
import com.alpaca.telalogin.ui.memes.adapter.ItemMemeAdapter.OnItemClickListener
import com.alpaca.telalogin.ui.memes.viewmodel.MemesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MemesFragment : Fragment(), OnItemClickListener {

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
        viewModel.listaDeMemes.observe(viewLifecycleOwner) {
            adapterMeme.atualizaLista(it)
        }
//        viewModel.memeLegendado.observe(viewLifecycleOwner) {
//            if (it.page_url.isNotEmpty()) {
//                findNavController().navigateWithAnimations(R.id.action_memesFragment_to_memeLegendadoFragment)
//            }
//        }
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
                    findNavController().navigate(action)
                }
                binding.progress.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }
}