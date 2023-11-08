package com.imdvlpr.expensetracker.helper.ui

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.setPadding
import com.imdvlpr.expensetracker.helper.utils.convertDpToPx

/**
 * Created by ramadhan on 2/27/18.
 */

/**
 * set visible view
 */
fun View.toVisible() {
    visibility = View.VISIBLE
}

/**
 * set gone
 */
fun View.toGone() {
    visibility = View.GONE
}

/**
 * check visible
 */
fun View.isVisible() : Boolean {
    return visibility == View.VISIBLE
}

/**
 * set visible view
 */
fun View.toEnable() {
    isEnabled = true
}

/**
 * set gone
 */
fun View.toDisable() {
    isEnabled = false
}

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.setInvisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}
fun Activity.showSoftKeyboard(editText: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.hideSoftKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun TextView.setStyledChip(textColor: Int, backgroundId: Int, textSize: Float = 14f) {
    val dp10 = convertDpToPx(10)
    val dp4 = convertDpToPx(4)
    this.textSize = textSize
    this.setPadding(dp10, dp4, dp10, dp4)
    this.setBackgroundResource(backgroundId)
    this.setTextColor(textColor)
}

fun TextView.setStyledChipSecond(textColor: Int, textSize: Float = 10f) {
    this.textSize = textSize
    this.setTextColor(textColor)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun ViewGroup.children() = object : Iterable<View> {
    override fun iterator() = object : Iterator<View> {
        var index = 0
        override fun hasNext() = index < childCount
        override fun next() = getChildAt(index++)
    }
}