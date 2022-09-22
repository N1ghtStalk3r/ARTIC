package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.core.utils.ImageLinkConstructor
import com.nightstalker.artic.databinding.ItemArtworkBinding
import com.nightstalker.artic.features.artwork.domain.Artwork

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListAdapter(
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<ArtworksListAdapter.ViewHolder>() {
    private var _data: List<Artwork> = mutableListOf()
    val data get() = _data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemArtworkBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_artwork, parent, false)
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.placeImage.context
        val item = _data[position]
        with(holder.binding) {
            textTitle.text = item.title

            val imageUrl = ImageLinkConstructor.createImageDefaultLink(item.imageId)
            Glide.with(context).load(imageUrl).into(placeImage)

            root.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int = _data.size

    fun setData(data: List<Artwork>) {
        if (data.isNotEmpty()) {
            this._data = data
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val binding: ItemArtworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: Artwork) {
//            with(binding) {
//                textTitle.text = item.title
//            }
//        }
    }
}