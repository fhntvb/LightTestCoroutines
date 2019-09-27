package com.imagine.therickandmorty.view.characters.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagine.therickandmorty.R
import android.app.Activity
import android.content.Intent
import com.imagine.therickandmorty.view.characters.CharactersFragment
import com.imagine.therickandmorty.view.characters.CharactersFragment.Companion.ARGUMENT_INTENT_BUNDLE_CHARACTER


class CharacterDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_details_activity)
        if (intent != null) {
            replaceFragment(intent)
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun replaceFragment(intent: Intent) {
        val characterModel = intent
            .getBundleExtra(ARGUMENT_INTENT_BUNDLE_CHARACTER)
            ?.getSerializable(CharactersFragment.ARGUMENT_BUNDLE_CHARACTER)

        val fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(R.id.content)
        if (fragment == null) {
            fragment = CharacterDetailsFragment()
            if (characterModel != null) {
                val args = Bundle()
                args.putSerializable(CharactersFragment.ARGUMENT_BUNDLE_CHARACTER, characterModel)
                fragment.setArguments(args)
            }
            fragmentManager.beginTransaction()
                .replace(R.id.content, fragment, CharacterDetailsFragment.FRAGMENT_TAG)
                .commit()
            fragmentManager.executePendingTransactions()
        }
    }
}