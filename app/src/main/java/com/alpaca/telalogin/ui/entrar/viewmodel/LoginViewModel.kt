package com.alpaca.telalogin.ui.entrar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpaca.telalogin.R
import com.alpaca.telalogin.model.Usuario
import com.alpaca.telalogin.repositorio.UsuarioRepositorio
import com.alpaca.telalogin.ui.entrar.viewmodel.LoginViewModel.EstadoAutenticacao.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(private val repositorio: UsuarioRepositorio) : ViewModel() {

    sealed class EstadoAutenticacao {
        object Valido : EstadoAutenticacao()
        object Invalido : EstadoAutenticacao()
        class CamposInvalidos(val camposInvalidos: List<Pair<String, Int>>) : EstadoAutenticacao()
    }

    val estadoAutenticacao = MutableLiveData<EstadoAutenticacao>()

    lateinit var usuarioLogado: LiveData<Usuario>

    fun checarUsuarioLogado() {
        usuarioLogado = repositorio.buscarPrimeiroUsuario()
    }

    fun registrarUsuario(usuario: String, senha: String) {
        val novoUsuario = Usuario(0, usuario, senha)
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.salvarUsuario(novoUsuario)
        }
    }

    fun desAutenticar() {
        estadoAutenticacao.value = Invalido
    }

    fun autenticar(usuario: String, senha: String) {
        if (isCamposValidos(usuario, senha)) {
            estadoAutenticacao.value = Valido
        }
    }

    private fun isCamposValidos(usuario: String, senha: String): Boolean {
        val camposInvalidos = arrayListOf<Pair<String, Int>>()

        if (usuario.trim().isEmpty()) {
            camposInvalidos.add(INPUT_USUARIO)
        }

        if (senha.trim().isEmpty()) {
            camposInvalidos.add(INPUT_SENHA)
        }

        if (camposInvalidos.isNotEmpty()) {
            estadoAutenticacao.value = CamposInvalidos(camposInvalidos)
            return true
        }
        return false
    }

    companion object {
        val INPUT_USUARIO = "INPUT_USUARIO" to R.string.texto_mensagem_erro_usuario_invalido
        val INPUT_SENHA = "INPUT_SENHA" to R.string.texto_mensagem_erro_senha_invalida
    }
}