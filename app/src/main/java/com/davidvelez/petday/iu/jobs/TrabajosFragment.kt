package com.davidvelez.petday.iu.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.R
import com.davidvelez.petday.databinding.FragmentInicioCuidadorBinding
import com.davidvelez.petday.databinding.FragmentTrabajosBinding
import com.davidvelez.petday.iu.services.InicioCuidadorFragmentDirections
import com.davidvelez.petday.iu.services.InicioCuidadorViewModel


class TrabajosFragment : Fragment() {
    private var _binding: FragmentTrabajosBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTrabajosBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(TrabajosFragmentDirections.actionTrabajosFragmentToServicesFragment())

        }
        return view




    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrabajosFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}