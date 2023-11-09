package com.turkoglu.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.turkoglu.artbooktesting.R
import com.turkoglu.artbooktesting.roomdb.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide : RequestManager

) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var onItemClickListener : ((String) -> Unit) ? = null
    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem== newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var images : List<String>
        get() = recyclerListDiffer.currentList
        set(value)  = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnItemClickListener(listener : (String)-> Unit){
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val imageView =holder.itemView.findViewById<ImageView>(R.id.imageView)
        val url = images[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }

        }


    }
}