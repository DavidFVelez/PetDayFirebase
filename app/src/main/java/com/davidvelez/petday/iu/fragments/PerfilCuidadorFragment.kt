package com.davidvelez.petday.iu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.davidvelez.petday.databinding.FragmentPerfilCuidadorBinding
import androidx.navigation.fragment.findNavController



class PerfilCuidadorFragment : Fragment() {
    private var _binding: FragmentPerfilCuidadorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val profileCarerViewModel = ViewModelProvider(this)[ProfileCarerViewModel::class.java]

        _binding = FragmentPerfilCuidadorBinding.inflate(inflater,container,false)
        val root: View = binding.root

        profileCarerViewModel.isSingOut.observe(viewLifecycleOwner) {
            val navController = findNavController()
            navController.navigate(PerfilCuidadorFragmentDirections.actionPerfilCuidadorFragmentToCarerLoginFragment())
            activity?.finish()
        }

        binding.signOutButton.setOnClickListener {
            profileCarerViewModel.signOut()


        }
        return root
    }
}


