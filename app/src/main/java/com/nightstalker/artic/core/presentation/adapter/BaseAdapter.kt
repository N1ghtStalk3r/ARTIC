package com.nightstalker.artic.core.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var itemsList: MutableList<T>? = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var expressionViewHolderBinding: ((T, ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }
            ?.let { BaseViewHolder(it, expressionViewHolderBinding!!) }!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(itemsList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }
}