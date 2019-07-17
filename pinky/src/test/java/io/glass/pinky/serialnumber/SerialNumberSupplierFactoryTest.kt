package io.glass.pinky.serialnumber

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class SerialNumberSupplierFactoryTest {

    @Config(sdk = [26])
    @Test
    fun buildShouldReturnOreoSerialNumberSupplierOnAndroidOreo() {
        val providerFactory = SerialNumberSupplierFactory.create()

        val idSupplier = providerFactory.build()

        assertTrue(idSupplier is OreoSerialNumberSupplier)
    }

    @Config(sdk = [25])
    @Test
    fun buildShouldReturnSerialNumberSupplierOnAndroidBeforeOreo() {
        val providerFactory = SerialNumberSupplierFactory.create()

        val idSupplier = providerFactory.build()

        assertTrue(idSupplier is SerialNumberSupplier)
    }
}