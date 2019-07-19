package io.glass.pinky.phoneid

import android.Manifest
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import io.glass.pinky.IdSupplier
import io.glass.pinky.SupplierFactory

/**
 * This class represents main factory which will provide proper strategy for accessing Device ID. For Android before
 * Oreo it will return strategy using [TelephonyManager.getDeviceId], for Android Oreo and after it will return
 * strategy using [TelephonyManager.getImei] or [TelephonyManager.getMeid]
 */
class PhoneIdSupplierFactory private constructor(private val telephonyManager: TelephonyManager) : SupplierFactory {

    override fun build(): IdSupplier {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OreoPhoneIdSupplier(telephonyManager)
        } else {
            PhoneIdSupplier(telephonyManager)
        }
    }

    companion object {

        @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
        @JvmStatic
        fun create(context: Context): PhoneIdSupplierFactory {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return PhoneIdSupplierFactory(telephonyManager)
        }
    }
}