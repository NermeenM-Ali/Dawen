package com.company.dawen.utils

import android.content.Context
import android.content.res.Configuration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Helpers {
    fun formattedDate(): String {
        return SimpleDateFormat("EEE d MMM yyyy h:mm a", Locale.getDefault()).format(Date())
    }

    fun isDarkMode(context: Context): Boolean {
        val darkModeFlag =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }

}