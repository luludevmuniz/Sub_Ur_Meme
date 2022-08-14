package com.alpaca.telalogin.repositorio

import com.alpaca.telalogin.api.ApiService
import com.alpaca.telalogin.db.dao.MemeDao
import com.alpaca.telalogin.model.Meme
import com.alpaca.telalogin.model.MemeLegendado
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