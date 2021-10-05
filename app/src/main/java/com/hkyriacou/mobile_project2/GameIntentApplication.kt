package com.hkyriacou.mobile_project2

import android.app.Application

class GameIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GameRepository.initialize(this)
    }
}