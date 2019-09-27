package com.imagine.therickandmorty.view.episode.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.domain.models.CharacterModel
import kotlinx.android.synthetic.main.item_character_grid.view.*
import java.lang.ref.WeakReference

class CharacterGridAdapter(
    private val contextReference: WeakReference<Context>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CharacterModel>()
    private val sf = CharactersModule.getStringFormatter()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character_grid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val characterHolder = holder as? CharacterViewHolder
        val item = items[position]

        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.image_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(characterHolder!!.avatar)

        val context = contextReference.get()
        if (context != null) {
            characterHolder.name?.text = sf.formatStat(
                context.resources.getString(R.string.character_name),
                item.name,
                context)
        }
    }

    fun replaceItems(characters: List<CharacterModel>) {
        items.clear()
        items.addAll(characters)
        notifyDataSetChanged()
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = itemView.avatar
        val name = itemView.name
    }
}