package com.nightstalker.artic.features.ticket.presentation.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.ItemTicketBinding
import com.nightstalker.artic.features.ticket.domain.TicketUseCase


/**
 * Адаптер для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketsListAdapter(private val onItemClicked: (id: Long) -> Unit) :
    RecyclerView.Adapter<TicketsListAdapter.ViewHolder>() {
    private var _data: List<TicketUseCase> = mutableListOf()
    val data get() = _data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTicketBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ticket, parent, false)
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.placeImage.context
        val item = _data[position]
        with(holder.binding) {
            textTitle.text = item.title
            val imageUrl = item.imageUrl
            Glide.with(context)
                .load(imageUrl)
                .onlyRetrieveFromCache(true)
                .into(placeImage)

            root.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int = _data.size

    fun setData(data: List<TicketUseCase>) {
        Log.d("TicketListAdapter", "list size = ${data.size} -- getItemCount() = ${getItemCount()}")
        if (data.isNotEmpty()) {
            this._data = data
            notifyDataSetChanged()
        }
        Log.d("TicketListAdapter", "list size = ${data.size} -- getItemCount() = ${getItemCount()}")
    }

    class ViewHolder(val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root)

}