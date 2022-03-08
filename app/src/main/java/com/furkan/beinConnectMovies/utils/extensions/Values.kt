package com.furkan.beinConnectMovies.utils.extensions

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.furkan.beinConnectMovies.app.App

fun string(id: Int): String {
    return App.instance.resources.getString(id)
}

fun color(id: Int): Int {
    return ContextCompat.getColor(App.instance, id)
}