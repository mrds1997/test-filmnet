package com.example.filmnettest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmnettest.R
import com.example.filmnettest.data.network.models.Video
import kotlinx.android.synthetic.main.adapter_video.view.*
import kotlin.collections.ArrayList


class VideoAdapter(private val items: ArrayList<Video>, private val onItemSelected: OnItemSelected) :RecyclerView.Adapter<VideoAdapter.DataObjectHolder>() {


    companion object {
        val TYPE_HEADER = 1
        val TYPE_NONE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_video, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {
        val context = holder.itemView.context
        val itemPosition = position /*- if (headerView == null) 0 else 1*/
        val viewType = getItemViewType(position)
        val item = items[itemPosition]

        Glide.with(context).load(item.posterImage?.path).into(holder.itemView.imgMovie)
        holder.itemView.tvTitle.text = item.title
        holder.itemView.tvDesc.text = item.summary
        holder.itemView.layoutMain.setOnClickListener {
            onItemSelected.onSelect(item.id)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemSelected {
        fun onSelect(id: String?)
    }
}