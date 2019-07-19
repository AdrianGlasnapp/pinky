package io.glass.pinky

/**
 * Responsible for accessing specific identifier which might be used for UUID generation.
 */
interface IdSupplier {

    /**
     * Returns specific device identifier.
     */
    fun supply(): String
}
