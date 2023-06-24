package com.example.imapp.global

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder

fun serializeWithExceptions(item: Any, exceptions: List<String>): String{
    val strategy: ExclusionStrategy = object : ExclusionStrategy {
        override fun shouldSkipField(field: FieldAttributes): Boolean {
            return exceptions.contains(field.name)
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }
    }

    val gsonBuilder = GsonBuilder()
        .addSerializationExclusionStrategy(strategy)
        .create()

    return gsonBuilder.toJson(item)
}
