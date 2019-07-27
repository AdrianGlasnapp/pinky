package io.glass.pinky.androidid

import android.content.Context
import io.glass.pinky.IdSupplier
import io.glass.pinky.SupplierFactory

/**
 * This class represents main factory which will provide proper strategy for accessing ANDROID_ID.
 *
 * Secure ANDROID_ID is randomly generated on a device first boot. It is 64-bit number available for both smartphones
 * and tablets. It may change if a factory reset is performed on the device.
 */
class AndroidIdSupplierFactory private constructor(private val context: Context) : SupplierFactory {

    override fun build(): IdSupplier {
        return AndroidIdSupplier(context)
    }

    companion object {

        @JvmStatic
        fun create(context: Context): AndroidIdSupplierFactory {
            return AndroidIdSupplierFactory(context)
        }
    }
}