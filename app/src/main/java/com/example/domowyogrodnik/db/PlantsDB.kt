package com.example.domowyogrodnik.db

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity
class PlantsDB : Serializable {
    //getters & setters
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "path")
    var path: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null
}