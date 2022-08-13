package com.alpaca.telalogin.ui.memes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alpaca.telalogin.databinding.ItemMemeFavoritoBinding
import com.alpaca.telalogin.model.MemeLegendado

class ItemMemeFavoritoAdapter(
    private var listaDeMemesLegendados: List<MemeLegendado>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemMemeFavoritoAdapter.ItemMemeFavoritoViewHolder>() {

    private lateinit var binding: ItemMemeFavoritoBinding

    interface OnItemClickListener {
        fun onItemClick(memeLegendado: MemeLegendado)
    }

    inner class ItemMemeFavoritoViewHolder(val binding: ItemMemeFavoritoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMemeFavoritoViewHolder {
        binding = ItemMemeFavoritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemMemeFavoritoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMemeFavoritoViewHolder, position: Int) {
        val memeLegendado = listaDeMemesLegendados[position]
        holder.binding.apply {
            imagemShapeableImageView.load(memeLegendado.url) {
                crossfade(true)
                crossfade(1000)
            }
            root.setOnClickListener {
                listener.onItemClick(memeLegendado)
            }
        }
    }

    override fun getItemCount() = listaDeMemesLegendados.size

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun atualizaLista(novaLista: List<MemeLegendado>) {
        listaDeMemesLegendados = novaLista
        notifyDataSetChanged()
    }
}