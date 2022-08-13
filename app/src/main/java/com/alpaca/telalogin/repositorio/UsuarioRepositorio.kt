package com.alpaca.telalogin.repositorio

import androidx.lifecycle.LiveData
import com.alpaca.telalogin.db.dao.UsuarioDao
import com.alpaca.telalogin.model.Usuario
import javax.inject.Inject

class UsuarioRepositorio @Inject constructor(private val usuarioDao: UsuarioDao) {

    fun buscarTodosUsuarios(): LiveData<List<Usuario>> {
        return usuarioDao.buscarTodos()
    }

    fun buscarPrimeiroUsuario() : LiveData<Usuario>{
        return usuarioDao.buscarPrimeiroUsuario()
    }

//    fun buscarUsuario(usuario: Usuario) : LiveData<Usuario>{
//        return usuarioDao.buscarUsuario(usuario)
//    }

    suspend fun salvarUsuario(usuario: Usuario){
        usuarioDao.salvar(usuario)
    }


}