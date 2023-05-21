package com.davidvelez.petday.iu.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.databinding.FragmentCarerRegisterBinding

class CarerRegisterFragment : Fragment() {


    private lateinit var carerRegisterBinding: FragmentCarerRegisterBinding
    private lateinit var carerRegisterViewModel: CarerRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carerRegisterBinding = FragmentCarerRegisterBinding.inflate(inflater, container, false)
        carerRegisterViewModel =
            ViewModelProvider(requireActivity()).get(CarerRegisterViewModel::class.java)
        return carerRegisterBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        carerRegisterViewModel.isSuccessSignUp.observe(viewLifecycleOwner) {
            findNavController().navigate(CarerRegisterFragmentDirections.actionCarerRegisterFragmentToCarerLoginFragment())
        }
        carerRegisterViewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        }
        carerRegisterBinding.botonRegistrarseFragmentRegistro.setOnClickListener {
            carerRegisterViewModel.validarContrasena(
                carerRegisterBinding.contrasenhaUsuarioRegisterEditText.text.toString(),
                carerRegisterBinding.repetirContrasenhaUsuarioRegisterEditText.text.toString(),
                carerRegisterBinding.correoRegisterEditText.text.toString()
            )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}

