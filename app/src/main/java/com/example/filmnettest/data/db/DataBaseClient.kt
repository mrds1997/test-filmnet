package com.example.filmnettest.data.db

import android.content.Context
import androidx.room.Room


public class DataBaseClient private constructor(mCtx: Context) {

    private val mCtx: Context


    //our app database object
    private val appDatabase: AppDataBase
    fun getAppDatabase(): AppDataBase {
        return appDatabase
    }

    companion object {
        private var mInstance: DataBaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DataBaseClient? {
            if (mInstance == null) {
                mInstance = DataBaseClient(mCtx)
            }
            return mInstance
        }
    }

    init {
        this.mCtx = mCtx

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDataBase::class.java, "App.db").build()
    }
}