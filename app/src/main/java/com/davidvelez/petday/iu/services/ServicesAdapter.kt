package com.davidvelez.petday.iu.services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidvelez.petday.Model.Service
import com.davidvelez.petday.R
import com.davidvelez.petday.databinding.CardViewServiceItemBinding

class ServicesAdapter (

    private val servicesList: ArrayList<Service>,
    private val onItemClicked: (Service) -> Unit,
    ):RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_service_item,parent,false)
        return ServicesViewHolder(view)

    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val service = servicesList[position]
        holder.bind(service)
        holder.itemView.setOnClickListener{onItemClicked(servicesList[position])}
    }


    override fun getItemCount(): Int =servicesList.size

    fun appendItems(newList: ArrayList<Service>){
            servicesList.clear()
            servicesList.addAll(newList)
            notifyDataSetChanged()

    }

    class ServicesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = CardViewServiceItemBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bind(service: Service){
            with(binding){
                nameServiceTextView.text= "Descripción: ${service.description}"
                costServiceTextView.text = "Precio:$ ${service.cost}"
                if(service.isCatSelected){
                    "Mascota: Gato".also { petTextCardView.text = it }
                }else {
                    petTextCardView.text = "Mascota: Perro"
                }
                if(service.isBanharSelected and !(service.isPaseoSelected) and !(service.isCuidarSelected)){
                    serviceTextCardView.text = "Se requiere cuidador para Baño de mascota"}

                else if(service.isPaseoSelected and !(service.isBanharSelected) and !(service.isCuidarSelected)){
                    serviceTextCardView.text = "Se requiere cuidador para Pasear mascota"
                }

                else if(service.isCuidarSelected and !(service.isPaseoSelected) and !(service.isBanharSelected)){
                    serviceTextCardView.text = "Se requiere cuidador para Cuidar mascota"
                }


                else if(service.isBanharSelected and service.isPaseoSelected and !(service.isCuidarSelected) ) {
                    serviceTextCardView.text = "Se requiere cuidador para pasear y bañar mascota"

                }

                else if(service.isBanharSelected and service.isCuidarSelected and !(service.isPaseoSelected)) {
                    serviceTextCardView.text = "Se requiere cuidador para cuidar y bañar mascota"

                }

                else if(service.isCuidarSelected and service.isPaseoSelected and !(service.isBanharSelected)) {
                    serviceTextCardView.text = "Se requiere cuidador para pasear y cuidar mascota" }


                else if(service.isCuidarSelected and service.isPaseoSelected and service.isBanharSelected) {
                    serviceTextCardView.text = "Se requiere cuidador para cuidar, pasear y bañar mascota"}

                else {
                    serviceTextCardView.text = "Se requiere servicio personalizado para mascota"
                }

            }

        }
    }
}