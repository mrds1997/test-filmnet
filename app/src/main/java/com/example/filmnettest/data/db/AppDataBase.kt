package com.example.filmnettest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.filmnettest.data.db.AppDao
import com.example.filmnettest.data.db.Video

@Database(entities = [Video::class], version = 14)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun dao(): AppDao?

}