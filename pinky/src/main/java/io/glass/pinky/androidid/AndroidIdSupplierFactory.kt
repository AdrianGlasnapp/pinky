package io.glass.pinky.androidid

import android.content.Context
import io.glass.pinky.IdSupplier
import io.glass.pinky.SupplierFactory

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