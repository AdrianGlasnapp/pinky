package io.glass.pinky.phoneid

import android.content.Context
import android.telephony.TelephonyManager
import androidx.test.core.app.ApplicationProvider
import org.apache.xerces.util.XMLSymbols.EMPTY_STRING
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowTelephonyManager

@RunWith(RobolectricTestRunner::class)
class PhoneIdSupplierTest {
    private lateinit var shadowTelephonyManager: ShadowTelephonyManager
    private lateinit var phoneIdSupplier: PhoneIdSupplier

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        shadowTelephonyManager = Shadows.shadowOf(telephonyManager)
        phoneIdSupplier = PhoneIdSupplier(telephonyManager)
    }

    @Config(sdk = [25], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsEmptyStringWhenSerialIsNull() {
        shadowTelephonyManager.setDeviceId(null)

        val observed = phoneIdSupplier.supply()

        assertEquals(EMPTY_STRING, observed)
    }

    @Config(sdk = [25], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsSerialWhenSerialIsPresent() {
        shadowTelephonyManager.setDeviceId(DEVICE_ID)

        val observed = phoneIdSupplier.supply()

        assertEquals(DEVICE_ID, observed)
    }

    companion object {
        const val DEVICE_ID = "20013fea6bcc820c"
    }
}