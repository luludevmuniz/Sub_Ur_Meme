package com.alpaca.suburmeme.repositorio

import com.alpaca.suburmeme.api.ApiService
import com.alpaca.suburmeme.db.dao.MemeDao
import com.alpaca.suburmeme.model.Meme
import com.alpaca.suburmeme.model.MemeLegendado
import javax.inject.Inject

class MemeRepositorio
@Inject
constructor(private val apiService: ApiService, private val memeDao: MemeDao) {

    suspend fun buscarMemesDaApi() = apiService.getMemes()

    suspend fun armazenarMeme(meme: Meme) = memeDao.armazenarLista(meme)

    fun buscarMemes() = memeDao.buscarTodosMemes()

    suspend fun legendarMeme(
        id: Int,
        login: String,
        senha: String,
        textoSuperior: String,
        textoInferior: String
    ) = apiService.legendarMeme(id, login, senha, textoSuperior, textoInferior)

    fun buscarMemesLegendado() = memeDao.buscarTodosMemesLegendados()

    suspend fun deletarMemeLegendado(memeLegendado: MemeLegendado) =
        memeDao.deletarMemeLegendado(memeLegendado = memeLegendado)

    suspend fun salvarMemeLegendado(memeLegendado: MemeLegendado) =
        memeDao.salvarMemeLegendado(memeLegendado = memeLegendado)

    fun procurarMemePeloNome(nome: String) = memeDao.procurarMemes(consulta = nome)

}