package com.example.demoapp.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    callback: DiffUtil.ItemCallback<T> = BaseDiffCallback()
): ListAdapter<T, BaseAdapter.BaseViewHolder<T>>(callback) {

    final override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindView(position, getItem(position))
    }

    final override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bindView(position, getItem(position), payloads)
    }

    abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(pos: Int, item: T)
        open fun bindView(pos: Int, item: T, payloads: MutableList<Any>) {
            // by default the payloads are ignored, just go with normal flow to update the whole view holder
            bindView(pos, item)
        }
    }

    open class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return false
        }

    }

}