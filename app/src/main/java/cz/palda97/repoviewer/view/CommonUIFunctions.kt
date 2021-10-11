package cz.palda97.repoviewer.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hide the keyboard
 * @param view The listener parameter
 */
fun Activity.hideSoftKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
}