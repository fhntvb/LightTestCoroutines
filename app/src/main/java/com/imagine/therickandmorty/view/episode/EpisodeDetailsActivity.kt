package com.imagine.therickandmorty.view.episode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.view.characters.details.CharacterDetailsFragment
import com.imagine.therickandmorty.view.characters.details.CharacterDetailsFragment.Companion.ARGUMENT_INTENT_BUNDLE_EPISODE

class EpisodeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.episode_details_activity)
        if (intent != null) {
            replaceFragment(intent)
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun replaceFragment(intent: Intent) {
        val episodeModel = intent
            .getBundleExtra(ARGUMENT_INTENT_BUNDLE_EPISODE)
            ?.getSerializable(CharacterDetailsFragment.ARGUMENT_BUNDLE_EPISODE)

        val fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(R.id.content)
        if (fragment == null) {
            fragment = EpisodeDetailsFragment()
            if (episodeModel != null) {
                val args = Bundle()
                args.putSerializable(CharacterDetailsFragment.ARGUMENT_BUNDLE_EPISODE, episodeModel)
                fragment.setArguments(args)
            }
            fragmentManager.beginTransaction()
                .replace(R.id.content, fragment, EpisodeDetailsFragment.FRAGMENT_TAG)
                .commit()
            fragmentManager.executePendingTransactions()
        }
    }
}