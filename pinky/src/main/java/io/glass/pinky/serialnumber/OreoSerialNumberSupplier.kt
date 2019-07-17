package io.glass.pinky.serialnumber

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import io.glass.pinky.IdSupplier
import io.glass.pinky.Strings

import android.Manifest.permission.READ_PHONE_STATE

internal class OreoSerialNumberSupplier : IdSupplier {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresPermission(READ_PHONE_STATE)
    override fun provide(): String {
        val serial = if (Build.UNKNOWN != Build.getSerial()) Build.getSerial() else Strings.EMPTY
        if (serial.isEmpty()) {
            return Strings.EMPTY
        }
        val board = if (Build.BOARD != null) Build.BOARD else Strings.EMPTY
        return board + serial
    }
}