package io.glass.pinky.serialnumber

import android.os.Build
import io.glass.pinky.IdSupplier
import io.glass.pinky.SupplierFactory

class SerialNumberSupplierFactory private constructor() : SupplierFactory {

    override fun build(): IdSupplier {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OreoSerialNumberSupplier()
        } else {
            SerialNumberSupplier()
        }
    }

    companion object {

        @JvmStatic
        fun create(): SerialNumberSupplierFactory {
            return SerialNumberSupplierFactory()
        }
    }
}
