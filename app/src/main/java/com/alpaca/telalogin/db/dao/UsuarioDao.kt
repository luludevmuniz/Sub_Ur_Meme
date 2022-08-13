package com.alpaca.telalogin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alpaca.telalogin.model.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM Usuarios")
    fun buscarTodos() : LiveData<List<Usuario>>

    @Query("SELECT * FROM Usuarios LIMIT 1")
    fun buscarPrimeiroUsuario() : LiveData<Usuario>

//    @Query("SELECT :usuario FROM usuario")
//    fun buscarUsuario(usuario: Usuario) : LiveData<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(usuario: Usuario)
}