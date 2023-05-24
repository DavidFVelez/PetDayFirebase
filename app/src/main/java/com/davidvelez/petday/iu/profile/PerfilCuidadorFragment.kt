package com.davidvelez.petday.iu.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.databinding.FragmentPerfilCuidadorBinding
import com.davidvelez.petday.iu.main.MainActivity
import com.davidvelez.petday.iu.profile.PerfilCuidadorFragmentDirections


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

        profileCarerViewModel.loadUserInfo()

        profileCarerViewModel.errorMsg.observe(viewLifecycleOwner){ errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        profileCarerViewModel.userLoaded.observe(viewLifecycleOwner){ user ->
            with(binding){
                nameProfileCarer.text = user?.name
                emailProfileCarer.text = user?.email
                phoneProfileCarer.text = user?.phone
            }
        }


        binding.signOutButton.setOnClickListener {
            profileCarerViewModel.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}