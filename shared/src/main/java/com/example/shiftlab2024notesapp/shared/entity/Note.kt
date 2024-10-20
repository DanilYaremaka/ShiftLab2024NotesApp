package com.example.shiftlab2024notesapp.shared.entity

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
@Serializable
data class Note(
    val id: Int? = null,
    val title: String = "",
    val text: String = "",
    val isFavourite: Boolean = false,
    val lastUpdate: Long? = null
) : Parcelable {

    fun dateToString(): String {
        val sdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
        return if (lastUpdate == null) ""
        else sdf.format(Date(lastUpdate))
    }

    companion object {

        val NavigationType: NavType<Note> = object : NavType<Note>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Note? {
                return Json.decodeFromString(bundle.getString(key) ?: return null)
            }

            override fun parseValue(value: String): Note {
                return Json.decodeFromString(Uri.decode(value))
            }

            override fun put(bundle: Bundle, key: String, value: Note) {
                bundle.putString(key, Json.encodeToString(value))
            }

            override fun serializeAsValue(value: Note): String {
                return Uri.encode(Json.encodeToString(value))
            }
        }
    }
}
