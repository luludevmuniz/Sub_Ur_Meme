package com.alpaca.telalogin.ui.entrar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alpaca.telalogin.databinding.FragmentLoginBinding
import com.alpaca.telalogin.extensions.dismissError
import com.alpaca.telalogin.ui.entrar.viewmodel.LoginViewModel
import com.alpaca.telalogin.ui.entrar.viewmodel.LoginViewModel.EstadoAutenticacao.*

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        configurarUsuarioLogado()
        configuraObserverEstadoAutenticacao()

        binding.loginMaterialButton.setOnClickListener { fazerLogin() }

        binding.usuarioEditText.addTextChangedListener {
            binding.usuarioTextInputLayout.dismissError()
        }
        binding.senhaEditText.addTextChangedListener {
            binding.senhaTextInputLayout.dismissError()
        }
    }

//    private fun configurarUsuarioLogado() {
//        loginViewModel.checarUsuarioLogado()
//        loginViewModel.usuarioLogado.observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.usuarioEditText.setText(it.login)
//                binding.senhaEditText.setText(it.senha)
//            }
//        }
//    }

    private fun configuraObserverEstadoAutenticacao() {
        loginViewModel.estadoAutenticacao.observe(viewLifecycleOwner) { estadoDaAutenticacao ->
            when (estadoDaAutenticacao) {
                is Valido -> {}
                is Invalido -> {}
                is CamposInvalidos -> {
                    mostraMensagemErro(estadoDaAutenticacao)
                }
            }
        }
    }

    private fun mostraMensagemErro(estadoDaAutenticacao: CamposInvalidos) {
        val camposParaValidar = initCamposValidacao()
        estadoDaAutenticacao.camposInvalidos.forEach { campoInvalido ->
            camposParaValidar[campoInvalido.first]?.error =
                getString(campoInvalido.second)
        }
    }

    private fun fazerLogin() {
        loginViewModel.autenticar(
            binding.usuarioEditText.text.toString(),
            binding.senhaEditText.text.toString()
        )
    }

    private fun initCamposValidacao() = mapOf(
        LoginViewModel.INPUT_USUARIO.first to binding.usuarioTextInputLayout,
        LoginViewModel.INPUT_SENHA.first to binding.senhaTextInputLayout
    )
}