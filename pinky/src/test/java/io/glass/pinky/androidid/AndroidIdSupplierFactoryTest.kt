package io.glass.pinky.androidid

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AndroidIdSupplierFactoryTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun buildShouldCreateAndroidIdProvider() {
        val providerFactory = AndroidIdSupplierFactory.create(context)

        val idSupplier = providerFactory.build()

        assertTrue(idSupplier is AndroidIdSupplier)
    }
}