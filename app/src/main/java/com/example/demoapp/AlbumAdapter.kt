package com.example.demoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.demoapp.base.BaseAdapter
import com.example.demoapp.models.ui.AlbumModel
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(callback: DiffUtil.ItemCallback<AlbumModel> = AlbumDiffCallback()): BaseAdapter<AlbumModel>(callback) {

    companion object {
        private const val PAYLOAD_BOOKMARK_CHANGE = "PAYLOAD_BOOKMARK_CHANGE"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AlbumModel> {
        return AlbumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false))
    }

    inner class AlbumViewHolder(view: View): BaseViewHolder<AlbumModel>(view) {

        @SuppressLint("SetTextI18n")
        override fun bindView(pos: Int, item: AlbumModel) {
            val album = item.data
            itemView.tvArtistName.text = album.artistName.orEmpty()
            itemView.tvCollectionName.text = album.collectionName.orEmpty()
            itemView.tvCollectionPrice.text = "${album.currency}$${album.collectionPrice}"
            itemView.tvCountry.text = album.country.orEmpty()
            itemView.tvReleaseDate.text = album.releaseDate.orEmpty()

            Glide.with(itemView).load(album.artworkUrl100.orEmpty()).into(itemView.ivArtist)

            itemView.cbBookmark.isChecked = item.isBookmarked

            itemView.cbBookmark.setOnClickListener {
                itemView.performClick()
            }
        }

        override fun bindView(pos: Int, item: AlbumModel, payloads: MutableList<Any>) {
            if (payloads.isNotEmpty()) {
                itemView.cbBookmark.isChecked = item.isBookmarked
            } else {
                super.bindView(pos, item, payloads)
            }
        }
    }

    class AlbumDiffCallback: BaseDiffCallback<AlbumModel>() {
        override fun areItemsTheSame(
            oldItem: AlbumModel,
            newItem: AlbumModel
        ): Boolean {
            return oldItem.data.collectionName == newItem.data.collectionName
        }

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.isBookmarked == newItem.isBookmarked
        }

        override fun getChangePayload(oldItem: AlbumModel, newItem: AlbumModel): Any? {
            return PAYLOAD_BOOKMARK_CHANGE
        }
    }
}