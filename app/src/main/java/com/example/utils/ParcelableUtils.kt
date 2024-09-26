package com.example.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

object ParcelableUtils {

    fun <T : Parcelable> getParcelableCompat(bundle: Bundle, key: String, dataType: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, dataType)
        } else {
            bundle.getParcelable(key)
        }
    }

}