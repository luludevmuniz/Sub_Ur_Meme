package com.alpaca.telalogin.ui.memes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpaca.telalogin.model.Meme
import com.alpaca.telalogin.model.MemeLegendado
import com.alpaca.telalogin.repositorio.MemeRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemesViewModel
@Inject
constructor(private val repositorio: MemeRepositorio) : ViewModel() {

    lateinit var listaDeMemes: LiveData<List<Meme>>
    lateinit var listaDeMemesLegendados: LiveData<List<MemeLegendado>>

    init {
        buscarListaDeMemesArmazenados()
        if (listaDeMemes.value.isNullOrEmpty()) {
            buscarListaDeMemesDaApi()
        }
    }

    private fun buscarListaDeMemesDaApi() =
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.buscarMemesDaApi().let { resposta ->
                if (resposta.isSuccessful) {
                    resposta.body()?.data?.memes?.let { listaDeMemes ->
                        listaDeMemes.forEach { meme ->
                            repositorio.armazenarMeme(meme)
                        }
                        buscarListaDeMemesArmazenados()
                    }
                } else {
                    Log.d("TAG", "Erro ao obter lista de memes: ${resposta.message()}")
                }
            }
        }

    private fun buscarListaDeMemesArmazenados() {
        listaDeMemes = repositorio.buscarMemes()
    }

    suspend fun legendarMeme(
        id: Int,
        textoSuperior: String,
        textoInferior: String
    ): MemeLegendado? {
        return withContext(Dispatchers.IO) {
            var memeLegendado: MemeLegendado? = null
            repositorio.legendarMeme(
                id,
                "Iulu",
                "Iuluimgfl1p",
                textoSuperior,
                textoInferior
            ).let { resposta ->
                if (resposta.isSuccessful) {
//                    memeLegendado.postValue(resposta.body()?.data)
                    memeLegendado = resposta.body()?.data
                } else {
                    Log.d("TAG", "Erro ao legendar memes: ${resposta.message()}")
                }
            }
            return@withContext memeLegendado
        }
    }

    fun buscarListaDeMemesLegendados() {
        listaDeMemesLegendados = repositorio.buscarMemesLegendado()
    }

    fun excluirMemeLegendado(memeLegendado: MemeLegendado) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.deletarMemeLegendado(memeLegendado)
        }
    }

    fun salvarMemeLegendado(memeLegendado: MemeLegendado) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.salvarMemeLegendado(memeLegendado)
        }
    }

    fun procurarMemePeloNome(nome: String): LiveData<List<Meme>> =
        repositorio.procurarMemePeloNome(nome)
}