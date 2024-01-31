package com.murtuza.popularmovielist.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonToObjectConverter {

    val gson = Gson()

    /**
     * Converting String JSON data into any given object.
     */
    inline fun <reified T> String?.fromJSONToObject(): T? {
        return try {
            this?.let {
                val type = object : TypeToken<T>() {}.type
                return gson.fromJson(it, type)
            } ?: kotlin.run {
                return null
            }
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Converting Kotlin Object into JSON string.
     */
    inline fun <reified T> T.fromObjectToJSON(): String? {
        return try {
            val type = object : TypeToken<T>() {}.type
            return gson.toJson(this, type)
        } catch (_: Exception) {
            null
        }
    }



    /**
     * Converting Any string array into list of provided object type.
     */
    inline fun <reified T> String?.convertStringArrayToListOfAnyObject(): List<T>? {
        return try {
            val type = object : TypeToken<ArrayList<T>>() {}.type
            gson.fromJson(this, type)
        } catch (e: Exception) {
            null
        }
    }




}

