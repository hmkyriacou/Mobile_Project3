package com.hkyriacou.mobile_project2

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_game")
data class Game(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var teamAName:String = "",
                var teamBName:String = "",
                var teamAScore:Int = 0,
                var teamBScore:Int = 0,
                var date: Date = Date(),
                //var teamAImage: ByteArray = ByteArray(0)
                //var teamAImage: ByteArray? = null
)  {
    val photoFileName
        get() = "IMG_$id.jpg"
}