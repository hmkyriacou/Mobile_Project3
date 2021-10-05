package com.hkyriacou.mobile_project2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hkyriacou.mobile_project2.Game
import java.util.*

@Dao
interface GameDao {
    @Query("SELECT * FROM TABLE_GAME")
    fun getGames(): LiveData<List<Game>>

    @Query("SELECT * FROM TABLE_GAME WHERE id=(:id)")
    fun getGame(id : UUID) : LiveData<Game?>

    @Update
    fun updateGame(game: Game)

    @Insert
    fun addGame(game: Game)
}