package com.imagine.therickandmorty.view.characters.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.domain.models.CharacterModel
import com.imagine.therickandmorty.domain.models.EpisodeModel
import com.imagine.therickandmorty.view.characters.CharactersFragment.Companion.ARGUMENT_BUNDLE_CHARACTER
import com.imagine.therickandmorty.view.characters.details.adapter.EpisodesAdapter
import com.imagine.therickandmorty.view.characters.listeners.OnEpisodeClickListener
import com.imagine.therickandmorty.view.episode.EpisodeDetailsActivity
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.fragment_character_details.recycler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class CharacterDetailsFragment : Fragment() {
    private lateinit var characterModel: CharacterModel
    private val sf = CharactersModule.getStringFormatter()
    private val repository = CharactersModule.getRepository()
    private lateinit var adapter: EpisodesAdapter
    private val uiScope = CoroutineScope(Dispatchers.Main)

    companion object {
        const val FRAGMENT_TAG = "character_details_fragment"
        const val ARGUMENT_BUNDLE_EPISODE = "character_details_episode"
        const val ARGUMENT_INTENT_BUNDLE_EPISODE = "epps"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            getFragmentArguments(arguments as Bundle)
        } else {
            activity?.finishActivity(Activity.RESULT_CANCELED)
        }
    }

    private fun getFragmentArguments(arguments: Bundle) {
        characterModel = arguments.getSerializable(ARGUMENT_BUNDLE_CHARACTER) as CharacterModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        initRecycler()
        getEpisodes()
    }

    private fun bindViews() {
        if (context != null) {
            Glide.with(context as Context)
                .load(characterModel.image)
                .placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatar)

            if (context != null) {
                name?.text = sf.formatStat(resources.getString(R.string.character_name),
                    characterModel.name,
                    context!!)
                status?.text = sf.formatStat(resources.getString(R.string.character_status),
                    characterModel.status,
                    context!!)
                species?.text = sf.formatStat(resources.getString(R.string.character_species),
                    characterModel.species,
                    context!!)
                gender?.text = sf.formatStat(resources.getString(R.string.character_gender),
                    characterModel.gender,
                    context!!)
            }
        }
    }

    private fun initRecycler() {
        if (context != null) {
            adapter = EpisodesAdapter(WeakReference(context!!), object : OnEpisodeClickListener {
                override fun onEpisodeClicked(episode: EpisodeModel) {
                    this@CharacterDetailsFragment.onEpisodeClicked(episode)
                }
            })
            val recyclerLayoutManager = LinearLayoutManager(context)
            recycler.layoutManager = recyclerLayoutManager
            recycler.adapter = adapter
        }
    }

    private fun getEpisodes() {
        uiScope.launch {
            progressBar.visibility = View.VISIBLE
            val episodes = repository.getEpisodesForCharacter(characterModel.episode)
            adapter.replaceItems(episodes)

            progressBar.visibility= View.GONE
        }
    }

    private fun onEpisodeClicked(episode: EpisodeModel) {
        moveToEpisodeDetails(episode)
    }

    private fun moveToEpisodeDetails(episode: EpisodeModel) {
        val intent = Intent(context, EpisodeDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(ARGUMENT_BUNDLE_EPISODE, episode)
        intent.putExtra(ARGUMENT_INTENT_BUNDLE_EPISODE, bundle)
        startActivityForResult(intent, Activity.RESULT_OK)
    }
}