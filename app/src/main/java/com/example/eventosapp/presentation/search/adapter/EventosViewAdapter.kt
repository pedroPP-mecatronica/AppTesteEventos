package com.example.eventosapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventosapp.R
import com.example.eventosapp.domain.extensions.setSafeOnClickListener
import com.example.eventosapp.domain.models.EventosModelResposta


class EventosViewAdapter(val eventos: MutableList<EventosModelResposta>) :
    RecyclerView.Adapter<EventosViewAdapter.ItensViewHolder>() {

    private var itemClickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }


    inner class ItensViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(evento: EventosModelResposta) {
            with(evento) {
                itemView.findViewById<TextView>(R.id.titulo).text = titulo

                itemView.findViewById<TextView>(R.id.preco).text =
                    " Preço: " + preco.toString() + "R$"
                itemView.setSafeOnClickListener {
                    itemClickListener?.onItemClick(it, layoutPosition)
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

    fun addItem(eventos: List<EventosModelResposta>) {
        this.eventos.clear()
        this.eventos.addAll(eventos)
        notifyDataSetChanged()
    }

}