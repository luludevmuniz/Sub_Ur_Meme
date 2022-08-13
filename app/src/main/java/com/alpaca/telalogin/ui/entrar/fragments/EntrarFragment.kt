package com.alpaca.telalogin.ui.entrar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alpaca.telalogin.R
import com.alpaca.telalogin.databinding.FragmentEntrarBinding
import com.alpaca.telalogin.extensions.navigateWithAnimations

class EntrarFragment : Fragment() {

    private lateinit var binding: FragmentEntrarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntrarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.entrarMaterialbutton.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_entrarFragment_to_loginFragment)
        }
    }

//    private fun registrar() {
//        loginViewModel.registrarUsuario(
//            binding.usuarioEditText.text.toString(),
//            binding.senhaEditText.text.toString()
//        )
//    }
}