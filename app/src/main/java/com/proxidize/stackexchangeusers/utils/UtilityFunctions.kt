package com.proxidize.stackexchangeusers.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun EditText.hideKeyboard() {
    val inputManager: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}
fun epochToIso8601(time: Long): String {
    val format = "dd MMM yyyy HH:mm:ss" // you can add the format you need
    val sdf = SimpleDateFormat(format, Locale.getDefault()) // default local
    sdf.timeZone = TimeZone.getDefault() // set anytime zone you need
    return sdf.format(Date(time * 1000))
}

fun View.showError(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}