package com.example.eventosapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventosapp.R
import com.example.eventosapp.data.remoto.modelos.EventosModelResponse
import com.example.eventosapp.presentation.SafeClickListener
import com.example.eventosapp.presentation.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_eventos.view.*


class EventosViewAdapter(val eventos: MutableList<EventosModelResponse>) :
    RecyclerView.Adapter<EventosViewAdapter.ItensViewHolder>() {

    private var itemClickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }


    inner class ItensViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(evento: EventosModelResponse) {
            with(evento) {
                itemView.titulo.text = titulo
                itemView.preco.text = " Preço: "+ preco.toString()+"R$"
                itemView.setSafeOnClickListener {
                    itemClickListener?.onItemClick(it,layoutPosition)
                }
            }
        }
    }

    fun itemClickListener(ic: ItemClickListener) {
        this.itemClickListener = ic
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItensViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_eventos, parent, false)
        return ItensViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItensViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    fun addItem(eventos: List<EventosModelResponse>) {
        this.eventos.clear()
        this.eventos.addAll(eventos)
        notifyDataSetChanged()
    }

}