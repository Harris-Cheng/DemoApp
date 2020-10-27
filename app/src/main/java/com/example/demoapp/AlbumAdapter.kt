package com.example.demoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.demoapp.base.BaseAdapter
import com.example.demoapp.models.responses.AlbumsResponse
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(callback: DiffUtil.ItemCallback<AlbumsResponse.Result> = AlbumDiffCallback()): BaseAdapter<AlbumsResponse.Result>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AlbumsResponse.Result> {
        return AlbumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false))
    }

    inner class AlbumViewHolder(view: View): BaseViewHolder<AlbumsResponse.Result>(view) {

        @SuppressLint("SetTextI18n")
        override fun bindView(pos: Int, item: AlbumsResponse.Result) {
            itemView.tvArtistName.text = item.artistName.orEmpty()
            itemView.tvCollectionName.text = item.collectionName.orEmpty()
            itemView.tvCollectionPrice.text = "${item.currency}$${item.collectionPrice}"
            itemView.tvCountry.text = item.country.orEmpty()
            itemView.tvReleaseDate.text = item.releaseDate.orEmpty()

            Glide.with(itemView).load(item.artworkUrl100.orEmpty()).into(itemView.ivArtist)
        }
    }

    class AlbumDiffCallback: BaseDiffCallback<AlbumsResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: AlbumsResponse.Result,
            newItem: AlbumsResponse.Result
        ): Boolean {
            return oldItem.collectionID == newItem.collectionID
        }
    }
}