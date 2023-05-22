package com.davidvelez.petday.iu.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.databinding.FragmentCarerLoginBinding


class CarerLoginFragment : Fragment() {

    private lateinit var carerLoginViewModel: CarerLoginViewModel
    private lateinit var carerLoginBinding: FragmentCarerLoginBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carerLoginBinding = FragmentCarerLoginBinding.inflate(inflater, container, false)
        val view = carerLoginBinding.root

        carerLoginViewModel = ViewModelProvider(requireActivity())[CarerLoginViewModel::class.java]


        carerLoginViewModel.isSuccessSignIn.observe(viewLifecycleOwner){
            findNavController().navigate(CarerLoginFragmentDirections.actionCarerLoginFragmentToBottomNavigationActivity())
        }
        carerLoginViewModel.errorMsg.observe(viewLifecycleOwner){ errorMsg ->
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        }

        carerLoginBinding.sinoHayCuenta.setOnClickListener {
            findNavController().navigate(CarerLoginFragmentDirections.actionCarerLoginFragmentToCarerRegisterFragment())
        }

        carerLoginBinding.entrar.setOnClickListener {

            carerLoginViewModel.validarDatos(
                carerLoginBinding.correoEditText.text.toString(),
                carerLoginBinding.contraseAEditText.text.toString()
                )
        }

        return view
    }
}



