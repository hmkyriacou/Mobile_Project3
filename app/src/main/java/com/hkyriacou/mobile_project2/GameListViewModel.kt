package com.hkyriacou.mobile_project2

import androidx.lifecycle.ViewModel

class GameListViewModel : ViewModel() {
    private val gameRepository = GameRepository.get()
    val gameListLiveData = gameRepository.getGames()
}