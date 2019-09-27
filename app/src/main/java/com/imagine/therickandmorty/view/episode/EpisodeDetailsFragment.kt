package com.imagine.therickandmorty.view.episode

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.domain.models.EpisodeModel
import com.imagine.therickandmorty.view.characters.details.CharacterDetailsFragment.Companion.ARGUMENT_BUNDLE_EPISODE
import com.imagine.therickandmorty.view.episode.adapter.CharacterGridAdapter
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class EpisodeDetailsFragment : Fragment() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val repository = CharactersModule.getRepository()

    private lateinit var episodeModel: EpisodeModel
    private lateinit var adapter: CharacterGridAdapter


    companion object {
        const val FRAGMENT_TAG = "episode_details_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            getFragmentArguments(arguments as Bundle)
        } else {
            activity?.finishActivity(Activity.RESULT_CANCELED)
        }
    }

    private fun getFragmentArguments(bundle: Bundle) {
        episodeModel = arguments?.getSerializable(ARGUMENT_BUNDLE_EPISODE) as EpisodeModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        getCharacters()
    }

    private fun initRecycler() {
        if (context != null) {
            adapter = CharacterGridAdapter(WeakReference(context!!))
            val recyclerLayoutManager = GridLayoutManager(context, 2)
            recycler.layoutManager = recyclerLayoutManager
            recycler.adapter = adapter
        }
    }

    private fun getCharacters() {
        uiScope.launch {
            progressBar.visibility = View.VISIBLE
            val characters = repository.getCharactersForEpisode(episodeModel.characters)
            adapter.replaceItems(characters)

            progressBar.visibility= View.GONE
        }
    }
}