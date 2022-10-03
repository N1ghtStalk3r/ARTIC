package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.ItemExhibitionBinding
import com.nightstalker.artic.features.exhibition.domain.Exhibition

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ExhibitionsListAdapter(
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<ExhibitionsListAdapter.ViewHolder>() {
    private var _data: List<Exhibition> = mutableListOf()
    val data get() = _data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemExhibitionBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_exhibition, parent, false)
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.placeImage.context
        val item = _data[position]
        with(holder.binding) {
            textTitle.text = item.title

            val imageUrl = item.imageUrl
            Glide.with(context).load(imageUrl).into(placeImage)

            root.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int = _data.size

    fun setData(data: List<Exhibition>) {
        if (data.isNotEmpty()) {
            this._data = data
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val binding: ItemExhibitionBinding) :
        RecyclerView.ViewHolder(binding.root)
}