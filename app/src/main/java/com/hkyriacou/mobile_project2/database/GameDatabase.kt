package com.hkyriacou.mobile_project2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hkyriacou.mobile_project2.Game

@Database(entities = [Game :: class], version = 1, exportSchema = false)
@TypeConverters(GameTypeConverters::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

}