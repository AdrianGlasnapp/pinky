package io.glass.pinky.serialnumber

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresPermission
import io.glass.pinky.IdSupplier
import io.glass.pinky.Strings

import android.Manifest.permission.READ_PHONE_STATE

internal class SerialNumberSupplier : IdSupplier {

    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    override fun supply(): String {
        val serial = if (Build.SERIAL != null) Build.SERIAL else Strings.EMPTY
        val board = if (Build.BOARD != null) Build.BOARD else Strings.EMPTY
        return board + serial
    }
}