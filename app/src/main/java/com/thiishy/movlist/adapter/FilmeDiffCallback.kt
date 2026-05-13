package com.thiishy.movlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.thiishy.movlist.model.Filme

class FilmeDiffCallback : DiffUtil.ItemCallback<Filme>() {
    override fun areItemsTheSame(
        oldItem: Filme,
        newItem: Filme
    ): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(
        oldItem: Filme,
        newItem: Filme
    ): Boolean {
        return oldItem == newItem
    }
}