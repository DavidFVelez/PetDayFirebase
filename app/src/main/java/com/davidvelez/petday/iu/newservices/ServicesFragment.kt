package com.davidvelez.petday.iu.newservices

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.davidvelez.petday.databinding.FragmentServicesBinding
import com.davidvelez.petday.iu.jobs.TrabajosFragmentDirections

class ServicesFragment : Fragment() {


    private lateinit var servicesViewModel: ServicesViewModel
    private lateinit var servicesBinding: FragmentServicesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        servicesBinding = FragmentServicesBinding.inflate(inflater, container, false)
        servicesViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]
        val view = servicesBinding.root

        with(servicesBinding){
            saveNewServiceButton.setOnClickListener{
                servicesViewModel.validateData(
                    catRadioButton.isChecked,
                    dogRadioButton.isChecked,
                    paseoNewService.isChecked,
                    cuidarNewService.isChecked,
                    banarNewService.isChecked,
                    descriptionNewServiceEditText.text.toString(),
                    costNewServiceEditText.text.toString(),
                    direccionEditText.text.toString()

                )
            }
        }

        servicesViewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        servicesViewModel.createServiceSuccess.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            //requireActivity().onBackPressedDispatcher.onBackPressed()
            findNavController().navigate(ServicesFragmentDirections.actionServicesFragmentToInicioCuidadorFragment())
            //activity?.finish()
        }



        return view
    }
}