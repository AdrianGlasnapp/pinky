package io.glass.pinky

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

/**
 * TODO
 */
class Pinky private constructor(
    private val salt: String,
    private val idSuppliers: List<IdSupplier>
) {

    fun generate(): UUID {
        val ids = StringBuilder()
        idSuppliers.forEach {
            ids.append(it.provide())
        }
        ids.ifEmpty { throw IllegalStateException("") }
        ids.append(salt)
        return UUID.nameUUIDFromBytes(sha256(ids.toString()))
    }

    private fun sha256(message: String) = MessageDigest
        .getInstance("SHA-256")
        .digest(message.toByteArray(StandardCharsets.UTF_8))

    class Builder {
        private val providerFactories = LinkedList<SupplierFactory>()
        private var salt = DEFAULT_SALT_32_CHARS

        fun salt(salt: String) = apply { this.salt = salt }

        fun addIdSupplierFactory(supplierFactory: SupplierFactory) = apply { providerFactories.add(supplierFactory) }

        fun build(): Pinky {
            providerFactories.ifEmpty { throw IllegalArgumentException("Missing ID provider factory.") }
            return Pinky(salt, providerFactories.map { it.build() })
        }
    }

    companion object {
        private const val DEFAULT_SALT_32_CHARS = "vWd\$dFQgAAjmK@ciH%bUpc2FKU@Z&cCk"
    }
}