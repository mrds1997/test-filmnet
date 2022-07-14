package com.example.filmnettest.data.db

import androidx.room.TypeConverter
import com.example.filmnettest.data.network.models.Category
import com.example.filmnettest.data.network.models.Image
import com.example.filmnettest.data.network.models.Item
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken




class Converter {

    @TypeConverter
    fun fromImage(image: Image): String?{
        return image.path
    }

    @TypeConverter
    fun toImage(path: String): Image{
        return Image(path)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<Category?>? {
        val listType = object : TypeToken<ArrayList<Category?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Category?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromItemTitle(value: String?): ArrayList<Item?>? {
        val listType = object : TypeToken<ArrayList<Item?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun  fromItems(list: ArrayList<Item?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}