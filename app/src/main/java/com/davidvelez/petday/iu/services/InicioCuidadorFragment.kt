package com.davidvelez.petday.iu.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.R
import com.davidvelez.petday.databinding.FragmentInicioCuidadorBinding
import com.davidvelez.petday.iu.newservices.ServicesViewModel

class InicioCuidadorFragment : Fragment() {

    private var _binding: FragmentInicioCuidadorBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioCuidadorBinding.inflate(inflater, container, false)
        val servicesViewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        val view = binding.root

        binding.servicesButton.setOnClickListener {
            findNavController().navigate(InicioCuidadorFragmentDirections.actionInicioCuidadorFragmentToServicesFragment())

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}