package io.glass.pinky.hardwareids

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class PhoneIdSupplierFactoryTest {
    private var context: Context = ApplicationProvider.getApplicationContext()

    @Config(sdk = [26])
    @Test
    fun buildShouldReturnOreoPhoneIdSupplierOnAndroidOreo() {
        val providerFactory = PhoneIdSupplierFactory.create(context)

        val idSupplier = providerFactory.build()

        assertTrue(idSupplier is OreoPhoneIdSupplier)
    }

    @Config(sdk = [25])
    @Test
    fun buildShouldReturnPhoneIdSupplierOnAndroidBeforeOreo() {
        val providerFactory = PhoneIdSupplierFactory.create(context)

        val idSupplier = providerFactory.build()

        assertTrue(idSupplier is PhoneIdSupplier)
    }
}