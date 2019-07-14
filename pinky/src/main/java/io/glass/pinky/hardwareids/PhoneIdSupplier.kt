package io.glass.pinky.hardwareids

import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import io.glass.pinky.IdSupplier
import io.glass.pinky.Strings

internal class PhoneIdSupplier(private val telephonyManager: TelephonyManager) : IdSupplier {

    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    override fun provide(): String {
        return telephonyManager.deviceId ?: Strings.EMPTY
    }
}