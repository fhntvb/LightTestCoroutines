package com.imagine.therickandmorty.view.characters.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.domain.models.EpisodeModel
import com.imagine.therickandmorty.view.characters.listeners.OnEpisodeClickListener
import kotlinx.android.synthetic.main.item_episode.view.*
import java.lang.ref.WeakReference

class EpisodesAdapter(
    private val contextReference: WeakReference<Context>,
    private val onEpisodeClickListener: OnEpisodeClickListener
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<EpisodeModel>()
    private val sf = CharactersModule.getStringFormatter()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val episodeViewHolder = holder as EpisodeViewHolder
        val item = items[position]

        val context = contextReference.get()
        if (context != null) {
            episodeViewHolder.name.text = sf.formatStat(context.resources.getString(R.string.episode_name),
                item.name,
                context)
            episodeViewHolder.airDate.text = sf.formatStat(context.resources.getString(R.string.episode_air_date),
                item.airDate,
                context)
            episodeViewHolder.episode.text = sf.formatStat(context.resources.getString(R.string.episode_episode),
                item.episode,
                context)
        }
        episodeViewHolder.itemView.setOnClickListener { onEpisodeClickListener.onEpisodeClicked(item) }
    }

    fun replaceItems(episodes: List<EpisodeModel>) {
        items.clear()
        items.addAll(episodes)
        notifyDataSetChanged()
    }

    class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = itemView.name
        val airDate= itemView.airDate
        val episode = itemView.episode
    }
}