package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.nightstalker.artic.R
import com.nightstalker.artic.core.utils.ImageLinkCreator
import com.nightstalker.artic.databinding.ItemArtworkBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListAdapter(
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<ArtworksListAdapter.ViewHolder>() {
    var data: MutableList<Artwork> = mutableListOf()
        set(newValue) {
            val diffCallback = ArtworksDiffCallback(data, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(newValue)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemArtworkBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_artwork, parent, false)
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.placeImage.context
        val item = data[position]
        with(holder.binding) {
            textTitle.text = item.title

            val imageUrl = item.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

            // Glide.with(context).load(imageUrl).into(placeImage)

            placeImage.load(imageUrl) {
                transformations(RoundedCornersTransformation(16f))
            }

            root.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(val binding: ItemArtworkBinding) :
        RecyclerView.ViewHolder(binding.root)
}

class ArtworksDiffCallback(
    private val oldList: List<Artwork>,
    private val newList: List<Artwork>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArtwork = oldList[oldItemPosition]
        val newArtwork = newList[newItemPosition]
        return oldArtwork.id == newArtwork.id    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArtwork = oldList[oldItemPosition]
        val newArtwork = newList[newItemPosition]
        return oldArtwork == newArtwork
    }
}