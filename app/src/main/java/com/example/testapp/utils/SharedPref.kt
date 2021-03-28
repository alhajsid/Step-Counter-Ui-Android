package com.example.testapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref (context: Context) {

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FITNESS", Context.MODE_PRIVATE)

    var steps: Float
        get() = sharedPreferences.getFloat("STEPS", 0f)
        set(bVal) {
            val editor = sharedPreferences.edit()
            editor.putFloat("STEPS", bVal)
            editor.apply()
        }
}
