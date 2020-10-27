package com.example.demoapp.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseAdapter<T>(
    callback: DiffUtil.ItemCallback<T> = BaseDiffCallback()
): ListAdapter<T, BaseAdapter.BaseViewHolder<T>>(callback) {

    companion object {
        const val ITEM_CLICK_BASE = 0
    }

    private val clickSubject = PublishSubject.create<ItemClick>()

    fun getClickObservable(): Observable<ItemClick> = clickSubject

    final override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {

        holder.bindView(position, getItem(position))
    }

    final override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.setClickSubject(clickSubject)
        holder.bindView(position, getItem(position), payloads)
    }

    abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var clickSubject: PublishSubject<ItemClick>

        abstract fun bindView(pos: Int, item: T)

        open fun bindView(pos: Int, item: T, payloads: MutableList<Any>) {
            // by default the payloads are ignored, just go with normal flow to update the whole view holder
            bindView(pos, item)
        }

        internal fun setClickSubject(clickSubject: PublishSubject<ItemClick>) {
            this.clickSubject = clickSubject

            itemView.clicks().map {
                ItemClick(
                    ITEM_CLICK_BASE,
                    adapterPosition
                )
            }.subscribe(clickSubject)
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