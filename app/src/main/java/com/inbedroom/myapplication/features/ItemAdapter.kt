package com.inbedroom.myapplication.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inbedroom.myapplication.databinding.RvItemBinding
import com.inbedroom.myapplication.model.MovieItemResponse

class ItemAdapter(private var itemList: List<MovieItemResponse>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = itemList[position])
    }

    override fun getItemCount(): Int = itemList.count()

    inner class ViewHolder(private val binding: RvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItemResponse) {
            if (!item.Poster.isNullOrBlank()) {
                Glide.with(itemView.context)
                    .load(item.Poster)
                    .into(binding.ivPoster)
            }
            binding.tvTitle.text = item.Title
        }
    }
}