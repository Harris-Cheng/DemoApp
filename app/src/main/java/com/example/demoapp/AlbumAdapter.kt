package com.example.demoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapp.models.responses.AlbumsResponse
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(callback: DiffUtil.ItemCallback<AlbumsResponse.Result> = AlbumDiffCallback()): ListAdapter<AlbumsResponse.Result, RecyclerView.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? AlbumViewHolder)?.bindView(getItem(position))
    }

    inner class AlbumViewHolder(view: View): RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindView(album: AlbumsResponse.Result) {
            itemView.tvArtistName.text = album.artistName.orEmpty()
            itemView.tvCollectionName.text = album.collectionName.orEmpty()
            itemView.tvCollectionPrice.text = "${album.currency}$${album.collectionPrice}"
            itemView.tvCountry.text = album.country.orEmpty()
            itemView.tvReleaseDate.text = album.releaseDate.orEmpty()

            Glide.with(itemView).load(album.artworkUrl100.orEmpty()).into(itemView.ivArtist)
        }
    }

    class AlbumDiffCallback: DiffUtil.ItemCallback<AlbumsResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: AlbumsResponse.Result,
            newItem: AlbumsResponse.Result
        ): Boolean {
            return oldItem.collectionID == newItem.collectionID
        }

        override fun areContentsTheSame(
            oldItem: AlbumsResponse.Result,
            newItem: AlbumsResponse.Result
        ): Boolean {
            return false
        }

    }
}