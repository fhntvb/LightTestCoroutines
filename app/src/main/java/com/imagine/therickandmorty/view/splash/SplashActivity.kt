package com.imagine.therickandmorty.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imagine.therickandmorty.R
import com.imagine.therickandmorty.view.characters.CharactersActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO) {
                delay(3000L)
                moveToCharactersList()
            }.await()
        }
    }

    private fun moveToCharactersList() {
        val intent = Intent(this, CharactersActivity::class.java)
        startActivity(intent)
        finish()
    }
}