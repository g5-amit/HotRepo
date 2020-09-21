package com.example.hotrepo.data.room.entity

import androidx.room.TypeConverter
import com.example.hotrepo.data.network.response.BuiltBy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Type Convertor are defined here to save objects in form of String Json to Room DB
 *
 */
class RepoConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromBuiltByJson(value: String?): List<BuiltBy?>? {
            if (value == null) {
                return null
            }
            val objType: Type = object : TypeToken<List<BuiltBy?>?>() {}.type
            return Gson().fromJson<List<BuiltBy?>>(value, objType)
        }

        @TypeConverter
        @JvmStatic
        fun toJsonString(builtByList: List<BuiltBy?>?): String? {
            if (builtByList == null) {
                return null
            }
            val gson = Gson()
            return gson.toJson(builtByList)
        }
    }
}