package com.example.shiftlab2024notesapp.shared.entity

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Note(
    val id: Int? = null,
    val title: String = "",
    val text: String = ""
) : Parcelable {
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
