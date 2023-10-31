package com.imdvlpr.expensetracker.helper.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Parcelable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.imdvlpr.expensetracker.R
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun encodeImage(bitmap: Bitmap): String {
    val previewWidth = 300
    val previewHeight = bitmap.height * previewWidth / bitmap.width
    val previewBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
    val byteArrayOutputStream = ByteArrayOutputStream()
    previewBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byte = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byte, Base64.DEFAULT)
}

fun decodeImage(encodedImage: String): Bitmap {
    val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
    val win: Window = activity.window
    val winParams: WindowManager.LayoutParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun setLocale(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val configuration = context.resources.configuration
    configuration.setLocale(locale)

    return updateResources(context, locale)
}

private fun updateResources(context: Context, locale: Locale): Context {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    return context.createConfigurationContext(configuration)
}

fun <T : Parcelable?> Intent.getParcelable(key: String, className: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= 33)
        this.getParcelableExtra(key, className)
    else
        this.getParcelableExtra(key)
}

fun showDatePicker(supportFragmentManager: FragmentManager, listener: DatePickerListener? = null) {
    var selectedDate = ""
    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = today
    calendar[Calendar.YEAR] = 1950
    val startYear = calendar.timeInMillis

    val constraintsBuilder =
        CalendarConstraints.Builder()
            .setStart(startYear)
            .setEnd(today)

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTheme(R.style.MaterialCalendarStyle)
        .setCalendarConstraints(constraintsBuilder.build())
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()
    datePicker.addOnPositiveButtonClickListener {
        calendar.timeInMillis = it
        selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
        listener?.onDateSelected(selectedDate)
    }
    datePicker.show(supportFragmentManager, "datePicker")
}

fun Context.setSpannable(
    fullString: String,
    keyword: String,
    listener: SpannableListener? = null,
    spanColor: Int = R.color.blue
): Spannable {
    val startPosition = fullString.indexOf(keyword)
    val spannable = SpannableStringBuilder(fullString)
    spannable.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this, spanColor)),
        startPosition, startPosition + keyword.length,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )

    if (listener != null) {
        // Set clickable span
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                listener.onClick()
            }
        }, startPosition, startPosition + keyword.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }

    return spannable
}

interface SpannableListener {
    fun onClick()
}

interface DatePickerListener {
    fun onDateSelected(s: String)
}