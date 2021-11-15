package com.example.sobes

import android.view.View
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import org.joda.time.format.DateTimeFormat

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

@ExperimentalCoroutinesApi
fun SearchView.searchMovie(): Flow<String?> {
    return callbackFlow {
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                trySend(newText)
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        }
        setOnQueryTextListener(listener)
        awaitClose { setOnQueryTextListener(null) }
    }.onStart { emit(query.toString()) }
}

fun String.toCorrectDate(): String =
    if (this.isEmpty()){
        this
    } else {
        DateTimeFormat.forPattern("dd.MM.yyyy").print(
            DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(this)
        ) ?: ""
    }

