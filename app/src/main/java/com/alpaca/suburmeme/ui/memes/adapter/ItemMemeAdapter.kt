package com.alpaca.suburmeme.ui.memes.adapter

import android.transition.Explode
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alpaca.suburmeme.databinding.ItemMemeBinding
import com.alpaca.suburmeme.model.Meme

class ItemMemeAdapter(
    private var listaDeMemes: List<Meme>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemMemeAdapter.ItemMemeViewHolder>() {

    private lateinit var binding: ItemMemeBinding
    private var textoSuperior: String = ""
    private var textoInferior: String = ""

    interface OnItemClickListener {
        fun onItemClick(id: Int, textoSuperior: String, textoInferior: String)
    }

    inner class ItemMemeViewHolder(val binding: ItemMemeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMemeViewHolder {
        binding = ItemMemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemMemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMemeViewHolder, position: Int) {
        val meme = listaDeMemes[position]
        holder.binding.apply {
            nomeMaterialTextView.text = meme.name
            imagemShapeableImageView.load(meme.url) {
                crossfade(false)
            }
            root.setOnClickListener {
                TransitionManager.beginDelayedTransition(root, Explode())
                if (legendarConstraintLayout.isVisible) {
                    legendarConstraintLayout.visibility = View.GONE
                } else {
                    legendarConstraintLayout.visibility = View.VISIBLE
                }
            }
            binding.textoSuperiorEditText.addTextChangedListener {
                textoSuperior = it.toString()
            }
            binding.textoInferiorEditText.addTextChangedListener {
                textoInferior = it.toString()
            }
            legendarMaterialButton.setOnClickListener {
                if (textoSuperior.trim().isEmpty()
                ) {
                    textoSuperiorTextInputLayout.error = "Digite alguma coisa"
                } else if (textoInferior.trim().isEmpty()) {
                    textoInferiorTextInputLayout.error = "Digite alguma coisa"
                } else {
                    listener.onItemClick(
                        meme.id,
                        textoSuperior,
                        textoInferior
                    )
                }
            }
        }
    }

    override fun getItemCount() = listaDeMemes.size

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun atualizaLista(novaLista: List<Meme>) {
        listaDeMemes = novaLista
        notifyDataSetChanged()
    }
}