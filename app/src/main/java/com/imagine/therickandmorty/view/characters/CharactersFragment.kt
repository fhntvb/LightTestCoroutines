package com.imagine.therickandmorty.view.characters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.domain.models.CharacterModel
import com.imagine.therickandmorty.view.characters.adapter.CharactersAdapter
import com.imagine.therickandmorty.view.characters.details.CharacterDetailsActivity
import com.imagine.therickandmorty.view.characters.listeners.OnCharacterClickListener
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class CharactersFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "characters_fragment"
        const val ARGUMENT_BUNDLE_CHARACTER = "arg_char"
        const val ARGUMENT_INTENT_BUNDLE_CHARACTER = "arg_int_bund"
        const val DETAILS_ACTIVITY_REQUEST_CODE = 777
    }

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val repository = CharactersModule.getRepository()
    private val dialog = CharactersModule.getDialogFactory()

    private lateinit var adapter: CharactersAdapter

    private var page = 1
    private var searchMode = false
    private val listOfCharacters = ArrayList<CharacterModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
        initRecyclerView()
        loadPosts()
    }

    private fun initSearchView() {
        search.setOnSearchClickListener {
            toolbarTitle.visibility = View.GONE
            searchMode = true
        }
        search.setOnCloseListener {
            toolbarTitle.visibility = View.VISIBLE
            search.isIconified = true
            searchMode = false
            false
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onQueryChange(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onQueryChange(newText)
                return true
            }
        })
    }

    private fun onQueryChange(query: String?) {
        if (!query.isNullOrEmpty() && query.length > 2) {
            uiScope.launch {
                progressBar.visibility = View.VISIBLE
                listOfCharacters.clear()
                val characters = repository.filterCharacters(query)
                listOfCharacters.addAll(characters)
                adapter.replaceItems(listOfCharacters)
                page = 1
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        adapter = CharactersAdapter(WeakReference(context!!), object : OnCharacterClickListener {
            override fun onCharacterClicked(character: CharacterModel) {
                this@CharactersFragment.onCharacterClicked(character)
            }
        })
        val recyclerLayoutManager = LinearLayoutManager(context)
        recycler.layoutManager = recyclerLayoutManager
        recycler.adapter = adapter

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isScrolling: Boolean = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemsCount = recyclerLayoutManager.itemCount
                val childItemsCount = recyclerLayoutManager.childCount
                val firstVisibleItemPosition = recyclerLayoutManager.findFirstVisibleItemPosition()

                if (isScrolling && childItemsCount + firstVisibleItemPosition == itemsCount) {
                    isScrolling = !isScrolling
                    loadPosts()
                }
            }
        })
    }

    private fun loadPosts() {
        if (!searchMode) {
            uiScope.launch {
                progressBar.visibility = View.VISIBLE
                val characters = repository.getCharacters(page)
                listOfCharacters.addAll(characters)
                adapter.replaceItems(listOfCharacters)
                page++
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun onCharacterClicked(character: CharacterModel) {
        moveToCharacterDetailsList(character)
    }

    private fun moveToCharacterDetailsList(character: CharacterModel) {
        val intent = Intent(context, CharacterDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(ARGUMENT_BUNDLE_CHARACTER, character)
        intent.putExtra(ARGUMENT_INTENT_BUNDLE_CHARACTER, bundle)
        startActivityForResult(intent, Activity.RESULT_OK)
    }
}