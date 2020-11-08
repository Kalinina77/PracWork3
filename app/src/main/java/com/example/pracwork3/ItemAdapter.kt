package com.example.pracwork3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter (
    private val context: Context,
    private val images: ArrayList<ItemOfList>,
    val listener: (ItemOfList) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleScr = view.findViewById<TextView>(R.id._Title)
        val ratingScr = view.findViewById<TextView>(R.id._Rating)
        val dateScr = view.findViewById<TextView>(R.id._Date)
        val imageScr = view.findViewById<ImageView>(R.id._Image)
        fun bindView(image: ItemOfList, listener: (ItemOfList) -> Unit){
            titleScr.text = image.title
            dateScr.text = image.release_date
            ratingScr.text = image.vote_average.toString()


            itemView.setOnClickListener { listener(image) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var currentItem = images.get(position)
        Picasso.with(context).load(currentItem.poster_path).fit().centerInside()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground).fit().into(holder.imageScr)
        holder.bindView(images[position], listener)
    }
}