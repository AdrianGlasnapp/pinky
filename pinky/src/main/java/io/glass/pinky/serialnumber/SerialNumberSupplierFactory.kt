package io.glass.pinky.serialnumber

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresPermission
import io.glass.pinky.IdSupplier
import io.glass.pinky.SupplierFactory

/**
 * This class represents main factory which will provide proper strategy for accessing Serial Number. The strategy
 * will be chosen based on Android version.
 *
 * Since Android 2.3 Gingerbread devices without telephony services must report unique device ID
 * which is obtainable via android.os.Build.SERIAL. Phones having telephony services can also
 * define a serial number, but it is not guaranteed to have it defined.
 */
class SerialNumberSupplierFactory private constructor() : SupplierFactory {

    override fun build(): IdSupplier {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OreoSerialNumberSupplier()
        } else {
            SerialNumberSupplier()
        }
    }

    companion object {

        @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
        @JvmStatic
        fun create(): SerialNumberSupplierFactory {
            return SerialNumberSupplierFactory()
        }
    }
}
