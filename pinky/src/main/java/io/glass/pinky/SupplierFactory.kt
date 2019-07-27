package io.glass.pinky

/**
 * Responsible for building [IdSupplier] for specific use case.
 */
interface SupplierFactory {

    fun build(): IdSupplier
}