package com.davidvelez.petday.iu.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidvelez.petday.Model.Service
import com.davidvelez.petday.R
import com.davidvelez.petday.databinding.FragmentInicioCuidadorBinding
import com.davidvelez.petday.iu.newservices.ServicesViewModel

class InicioCuidadorFragment : Fragment() {

    private var _binding: FragmentInicioCuidadorBinding? =null
    private val binding get() = _binding!!

    private lateinit var inicioCuidadorViewModel: InicioCuidadorViewModel
    private lateinit var servicesAdapter: ServicesAdapter
    private var servicesList: ArrayList<Service> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioCuidadorBinding.inflate(inflater, container, false)
        val inicioCuidadorViewModel = ViewModelProvider(this).get(InicioCuidadorViewModel::class.java)
        val view = binding.root

        binding.servicesButton.setOnClickListener {
            findNavController().navigate(InicioCuidadorFragmentDirections.actionInicioCuidadorFragmentToServicesFragment())

        }

        servicesAdapter = ServicesAdapter(servicesList,
            onItemClicked = {}
        )
        binding.servicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@InicioCuidadorFragment.requireContext())
            adapter = servicesAdapter
            setHasFixedSize(false)
        }

        inicioCuidadorViewModel.loadServices()

        inicioCuidadorViewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        inicioCuidadorViewModel.servicesList.observe(viewLifecycleOwner) { serviceList ->
            servicesAdapter.appendItems(serviceList)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}