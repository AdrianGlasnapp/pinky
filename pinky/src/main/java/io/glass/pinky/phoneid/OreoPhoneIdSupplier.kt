package io.glass.pinky.phoneid

import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import io.glass.pinky.IdSupplier
import io.glass.pinky.Strings

import android.Manifest.permission.READ_PHONE_STATE

internal class OreoPhoneIdSupplier(private val telephonyManager: TelephonyManager) : IdSupplier {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresPermission(READ_PHONE_STATE)
    override fun supply(): String {
        if (telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_GSM) {
            return if (telephonyManager.imei != null) telephonyManager.imei else Strings.EMPTY
        } else if (telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_CDMA) {
            return if (telephonyManager.meid != null) telephonyManager.meid else Strings.EMPTY
        }
        return Strings.EMPTY
    }
}
