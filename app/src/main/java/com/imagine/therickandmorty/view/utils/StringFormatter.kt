package com.imagine.therickandmorty.view.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.imagine.therickandmorty.R

class StringFormatter {
    fun formatStat(preName: String, name: String, context: Context): Spannable {
        val str = SpannableStringBuilder(preName)
        str.append(" ")
        str.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorBlack)),
            0,
            str.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        str.append(name)
        str.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
            str.length - name.length,
            str.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return str
    }
}