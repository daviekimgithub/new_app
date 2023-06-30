package com.example.newsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppsAdapter(val context: Context, private val gamesList: List<App>) :
    RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = holder.itemView
        val games = gamesList[position]

        val title = itemView.findViewById(R.id.item_title) as TextView
        val play = itemView.findViewById(R.id.item_play) as TextView
        val image = itemView.findViewById(R.id.item_image) as ImageView
        val summary = itemView.findViewById(R.id.item_review_summary) as TextView
        val price = itemView.findViewById(R.id.item_price) as TextView
        val date = itemView.findViewById(R.id.item_date) as TextView

        title.text = games.title
        summary.text = games.reviewSummary.replace("<", "&lt;").replace(">", "&gt;")
        price.text = games.price
        date.text = games.released
        // Picasso.with(itemView.context).load(games.imgUrl).into(image)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
