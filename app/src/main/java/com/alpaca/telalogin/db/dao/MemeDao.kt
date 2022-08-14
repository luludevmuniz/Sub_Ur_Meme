package com.alpaca.telalogin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alpaca.telalogin.model.Meme
import com.alpaca.telalogin.model.MemeLegendado

@Dao
interface MemeDao {

    @Query("SELECT * FROM Memes")
    fun buscarTodosMemes(): LiveData<List<Meme>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun armazenarLista(listaDeMemes: Meme)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvarMemeLegendado(memeLegendado: MemeLegendado)

    @Delete
    suspend fun deletarMemeLegendado(memeLegendado: MemeLegendado)

    @Query("SELECT * FROM Memelegendado")
    fun buscarTodosMemesLegendados(): LiveData<List<MemeLegendado>>

    @Update
    suspend fun atualizarMemeLegendadoComBitmap(memeLegendado: MemeLegendado)

    @Query("SELECT * FROM Memes WHERE nome LIKE :consulta")
    fun procurarMemes(consulta: String): LiveData<List<Meme>>
}