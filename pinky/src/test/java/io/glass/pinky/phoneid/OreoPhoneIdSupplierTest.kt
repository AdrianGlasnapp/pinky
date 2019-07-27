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
class OreoPhoneIdSupplierTest {
    private lateinit var shadowTelephonyManager: ShadowTelephonyManager
    private lateinit var oreoPhoneIdSupplier: OreoPhoneIdSupplier

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        shadowTelephonyManager = Shadows.shadowOf(telephonyManager)
        oreoPhoneIdSupplier = OreoPhoneIdSupplier(telephonyManager)
    }

    @Config(sdk = [26], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsImeiForGsmType() {
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_GSM)
        shadowTelephonyManager.setImei(IMEI)

        val observed = oreoPhoneIdSupplier.supply()

        assertEquals(IMEI, observed)
    }

    @Config(sdk = [26], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsEmptyStringWhenImeiIsUnavailable() {
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_GSM)
        shadowTelephonyManager.setImei(null)

        val observed = oreoPhoneIdSupplier.supply()

        assertEquals(EMPTY_STRING, observed)
    }

    @Config(sdk = [26], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsMeidForCdmaType() {
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_CDMA)
        shadowTelephonyManager.setMeid(MEID)

        val observed = oreoPhoneIdSupplier.supply()

        assertEquals(MEID, observed)
    }

    @Config(sdk = [26], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsEmptyStringWhenMeidIsUnavailable() {
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_CDMA)
        shadowTelephonyManager.setMeid(null)

        val observed = oreoPhoneIdSupplier.supply()

        assertEquals(EMPTY_STRING, observed)
    }

    @Config(sdk = [26], shadows = [ShadowTelephonyManager::class])
    @Test
    fun provideReturnsEmptyStringWhenThereIsNoAvailableId() {
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_NONE)

        val observed = oreoPhoneIdSupplier.supply()

        assertEquals(EMPTY_STRING, observed)
    }

    companion object {
        const val IMEI = "990000862471854"
        const val MEID = "A0000011223456"
    }
}