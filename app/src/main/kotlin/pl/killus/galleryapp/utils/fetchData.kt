package pl.killus.galleryapp.utils

import android.content.ContentResolver
import android.net.Uri

fun <T> ContentResolver.fetchData(
    location: Uri,
    data: Array<String>,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null,
    map: (List<String>) -> T
): MutableList<T> =
    mutableListOf<T>().also {
        query(
            location, data, selection, selectionArgs, sortOrder
        )?.use { c ->
            while (c.moveToNext()) it.add(map(data.map { c.getString(c.getColumnIndexOrThrow(it)) }))
        }
    }