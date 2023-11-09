package com.imdvlpr.sobatdompet.helper.network

import eu.amirs.JSON
import org.json.JSONObject


fun jsonHasKey(jsonString: String, key: String): Boolean {
    return when{
        jsonString.isEmpty() -> false
        jsonString.startsWith("{") && jsonString.endsWith("}") -> {
            val json = JSONObject(jsonString)
            json.has(key)
        }
        else -> false
    }
}

fun JSON.getList(param: String = ""): ArrayList<JSON> {
    val listResponse = ArrayList<JSON>()
    if (param.isEmpty()) {
        for (i in 0 until this.count()) {
            listResponse.add(this.index(i))
        }
    } else if (jsonHasKey(this.toString(), param)) {
        val jsonResponse = this.key(param)
        for (i in 0 until jsonResponse.count()) {
            listResponse.add(jsonResponse.index(i))
        }
    }
    return listResponse
}

fun JSON.getString(param: String): String {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).stringValue()
        else -> return ""
    }
}

fun JSON.getInt(param: String): Int {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).intValue()
        else -> return 0
    }
}

fun JSON.getBoolean(param: String): Boolean {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> {
            when (JSONObject(response).get(param)) {
                is Boolean -> this.key(param).booleanValue()
                is String -> this.getString(param) == "true"
                is Int -> this.getInt(param) == 1
                else -> false
            }
        }
        else -> false
    }
}

fun JSON.getDouble(param: String): Double {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).doubleValue()
        else -> return 0.0
    }
}

fun JSON.getLong(param: String): Long {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).longValue()
        else -> return 0
    }
}

fun JSON.getKey(param: String): JSON {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param)
        else -> return JSON("")
    }
}

fun JSON.getIntOrNull(param: String): Int? {
    val response = this.toString()
    return when{
        jsonHasKey(response, param) -> this.key(param).intValue()
        else -> return null
    }
}