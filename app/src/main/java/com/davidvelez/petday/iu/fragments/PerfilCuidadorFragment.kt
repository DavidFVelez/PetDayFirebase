package com.davidvelez.petday.iu.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.R
import com.davidvelez.petday.databinding.FragmentPerfilCuidadorBinding
import com.davidvelez.petday.iu.login.CarerLoginFragmentDirections


class PerfilCuidadorFragment : Fragment() {
    private var _binding: FragmentPerfilCuidadorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileCarerViewModel = ViewModelProvider(this)[ProfileCarerViewModel::class.java]

        _binding = FragmentPerfilCuidadorBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.signOutButton.setOnClickListener {
            profileCarerViewModel.signOut()
            findNavController().navigate(PerfilCuidadorFragmentDirections.actionPerfilCuidadorFragmentToCarerLoginFragment())
            activity?.finish()

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}