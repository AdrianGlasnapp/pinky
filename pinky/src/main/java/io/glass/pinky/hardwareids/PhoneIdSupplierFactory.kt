package io.glass.pinky.hardwareids

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import io.glass.pinky.IdSupplier
import io.glass.pinky.SupplierFactory

class PhoneIdSupplierFactory private constructor(private val telephonyManager: TelephonyManager) : SupplierFactory {

    override fun build(): IdSupplier {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OreoPhoneIdSupplier(telephonyManager)
        } else {
            PhoneIdSupplier(telephonyManager)
        }
    }

    companion object {

        @JvmStatic
        fun create(context: Context): PhoneIdSupplierFactory {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return PhoneIdSupplierFactory(telephonyManager)
        }
    }
}