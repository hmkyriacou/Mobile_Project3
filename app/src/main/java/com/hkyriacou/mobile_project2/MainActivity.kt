package com.hkyriacou.mobile_project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val TAG = "MainActivity"
private const val KEY_HOMESCORE = "homeScore"
private const val KEY_AWAYSCORE = "guestScore"

class MainActivity : AppCompatActivity(), LandingFragment.Callbacks, SavingFragment.Callbacks, GameListFragment.Callbacks {

    private val basketballViewModel: BasketballViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putInt(KEY_HOMESCORE, basketballViewModel.homeScore)
        outState.putInt(KEY_AWAYSCORE, basketballViewModel.guestScore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Creating Activity Instance")
        setContentView(R.layout.activity_main)

        val hScoreInstance = savedInstanceState?.getInt(KEY_HOMESCORE) ?: 0
        val aScoreInstance = savedInstanceState?.getInt(KEY_AWAYSCORE) ?: 0
        basketballViewModel.homeScore = hScoreInstance
        basketballViewModel.guestScore = aScoreInstance

        val currentFragment = supportFragmentManager.findFragmentById(R.id.frag_container)

        if (currentFragment == null) {
            val fragment = LandingFragment.newInstance(null, "WORLD")
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frag_container, fragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroying MainActivity")
    }

    override fun moveToSavingFragment() {
        Log.d(TAG, "Moving to SavingFragment")
        val fragment = SavingFragment.newInstance("HELLO", "WORLD")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_container, fragment)
            .commit()
    }

    override fun back() {
        Log.d(TAG, "Moving back to LandingFragment")
        val fragment = LandingFragment.newInstance(null, "WORLD")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_container, fragment)
            .commit()
    }

    override fun moveToGameListFragment() {
        Log.d(TAG, "Moving to GameListFragment")
        val fragment = GameListFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_container, fragment)
            .commit()
    }

    override fun backToLanding() {
        Log.d(TAG, "Moving back to landing page")
        val fragment = LandingFragment.newInstance(null, "WORLD")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_container, fragment)
            .commit()
    }

    override fun onGameSelected(gameId: UUID) {
        Log.d(TAG,"main activity got game selected $gameId" )
        val fragment = LandingFragment.newInstance(gameId, "WORLD")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()


    }
}