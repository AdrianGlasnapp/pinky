package io.glass.pinky

interface SupplierFactory {

    fun build(): IdSupplier
}