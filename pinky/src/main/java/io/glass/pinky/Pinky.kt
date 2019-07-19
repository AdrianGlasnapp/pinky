package io.glass.pinky

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

/**
 * Pinky helps to identify a particular device by generating UUID based on hardware data.
 *
 * Supported IdSuppliers:
 * - Serial Number - [io.glass.pinky.serialnumber.SerialNumberSupplierFactory]
 * - ANDROID_ID - [io.glass.pinky.androidid.AndroidIdSupplierFactory]
 * - Unique telephony ID - [io.glass.pinky.phoneid.PhoneIdSupplierFactory]
 */
class Pinky private constructor(
    private val salt: String,
    private val idSuppliers: List<IdSupplier>
) {

    /**
     * Returns a unique UUID based on defined id suppliers.
     *
     * @throws NoAvailableIdException when there is not any available id
     */
    fun generate(): UUID {
        return idSuppliers
            .joinToString { it.supply() }
            .ifEmpty { throw NoAvailableIdException() }
            .plus(salt)
            .let { UUID.nameUUIDFromBytes(sha256(it)) }
    }

    private fun sha256(message: String) = MessageDigest
        .getInstance("SHA-256")
        .digest(message.toByteArray(StandardCharsets.UTF_8))

    class Builder {
        private val supplierFactories = LinkedList<SupplierFactory>()
        private var salt = DEFAULT_SALT_32_CHARS

        fun salt(salt: String) = apply { this.salt = salt }

        fun addIdSupplierFactory(supplierFactory: SupplierFactory) = apply { supplierFactories.add(supplierFactory) }

        fun build(): Pinky {
            return supplierFactories
                .ifEmpty { throw IllegalArgumentException("Missing ID provider factory.") }
                .map { it.build() }
                .let {
                    Pinky(salt, it)
                }
        }
    }

    companion object {
        private const val DEFAULT_SALT_32_CHARS = "vWd\$dFQgAAjmK@ciH%bUpc2FKU@Z&cCk"
    }
}