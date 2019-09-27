package com.imagine.therickandmorty.view.characters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.domain.models.CharacterModel
import com.imagine.therickandmorty.view.characters.listeners.OnCharacterClickListener
import kotlinx.android.synthetic.main.item_character.view.*
import java.lang.ref.WeakReference

class CharactersAdapter(

    private val contextReference: WeakReference<Context>,
    private val onCharacterClicked: OnCharacterClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CharacterModel>()
    private val sf = CharactersModule.getStringFormatter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
    )

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
            characterHolder.status?.text = sf.formatStat(context.resources.getString(R.string.character_status),
                item.status,
                context)
            characterHolder.species?.text = sf.formatStat(context.resources.getString(R.string.character_species),
                item.status,
                context)
            characterHolder.gender?.text = sf.formatStat(context.resources.getString(R.string.character_gender),
                item.status,
                context)
            characterHolder.itemView.setOnClickListener { onCharacterClicked.onCharacterClicked(item) }
        }
    }

    override fun getItemCount() = items.size

    fun replaceItems(characters: List<CharacterModel>) {
        items.clear()
        items.addAll(characters)
        notifyDataSetChanged()
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.avatar
        val name = view.name
        val status = view.status
        val species = view.species
        val gender = view.gender
    }
}