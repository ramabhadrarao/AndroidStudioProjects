package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Song
import com.example.myapplication.databinding.ItemSongBinding

class SongAdapter(private val onSongClick: (Song) -> Unit) :
    ListAdapter<Song, SongAdapter.SongViewHolder>(SongDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
    }

    inner class SongViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.textTitle.text = "${song.titleEnglish} / ${song.titleTelugu}"
            binding.textRagam.text = "${song.ragamEnglish} - ${song.talamEnglish}"
            binding.root.setOnClickListener { onSongClick(song) }
        }
    }

    class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem == newItem
    }
}
