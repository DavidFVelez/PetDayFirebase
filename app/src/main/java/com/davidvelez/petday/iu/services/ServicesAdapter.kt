package com.davidvelez.petday.iu.services

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
        fun bind(service: Service){
            with(binding){
                nameServiceTextView.text= service.description
                costServiceTextView.text = "Precio: ${service.cost}"

            }

        }
    }
}