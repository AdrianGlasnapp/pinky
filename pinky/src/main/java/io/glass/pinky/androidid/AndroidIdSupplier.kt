package io.glass.pinky.androidid

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import io.glass.pinky.IdSupplier
import io.glass.pinky.Strings

internal class AndroidIdSupplier(private val context: Context) : IdSupplier {

    @SuppressLint("HardwareIds")
    override fun supply(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: Strings.EMPTY
    }
}