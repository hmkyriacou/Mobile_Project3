package com.hkyriacou.mobile_project2

import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.Serializable

private const val TAG = "BasketballViewModel"

class BasketballViewModel : ViewModel(), Serializable {
    var homeScore = 0;
    var guestScore = 0;

    fun incHomeScore(i : Int) : Int{
        homeScore+=i
        return homeScore
    }

    fun incGuestScore(i : Int) : Int{
        guestScore+=i
        return guestScore
    }

    fun resetHome() : Int{
        homeScore = 0
        return homeScore
    }

    fun resetAway() : Int{
        guestScore = 0
        return guestScore
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()

        Log.d(TAG, "ViewModel instance destroyed")
    }
}