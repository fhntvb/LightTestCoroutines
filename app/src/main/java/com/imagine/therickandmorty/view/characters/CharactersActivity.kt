package com.imagine.therickandmorty.view.characters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagine.therickandmorty.R

class CharactersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.characters_activity)
        replaceFragment()
    }

    private fun replaceFragment() {
        val fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(R.id.content)
        if (fragment == null) {
            fragment = CharactersFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.content, fragment, CharactersFragment.FRAGMENT_TAG)
                .commit()
            fragmentManager.executePendingTransactions()
        }
    }
}
