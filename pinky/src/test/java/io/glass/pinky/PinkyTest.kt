package io.glass.pinky

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.test.core.app.ApplicationProvider
import io.glass.pinky.androidid.AndroidIdSupplierFactory
import io.glass.pinky.androidid.AndroidIdSupplierTest.Companion.ANDROID_ID
import io.glass.pinky.hardwareids.OreoPhoneIdSupplierTest.Companion.IMEI
import io.glass.pinky.hardwareids.OreoPhoneIdSupplierTest.Companion.MEID
import io.glass.pinky.hardwareids.PhoneIdSupplierFactory
import io.glass.pinky.hardwareids.PhoneIdSupplierTest.Companion.DEVICE_ID
import io.glass.pinky.serialnumber.OreoSerialNumberSupplierTest.Companion.BOARD
import io.glass.pinky.serialnumber.OreoSerialNumberSupplierTest.Companion.SERIAL
import io.glass.pinky.serialnumber.SerialNumberSupplierFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowTelephonyManager
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class PinkyTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var shadowTelephonyManager: ShadowTelephonyManager
    private lateinit var contentResolver: ContentResolver

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        shadowTelephonyManager = Shadows.shadowOf(telephonyManager)
        contentResolver = context.contentResolver
    }

    @Test(expected = IllegalArgumentException::class)
    fun `build() throws exception if there is no ID provider configured`() {
        Pinky.Builder().build()
    }

    @Config(sdk = [26])
    @Test(expected = IllegalStateException::class)
    fun `generate() throws exception if there is not any ID available`() {
        val pinky = Pinky.Builder()
            .addIdSupplierFactory(SerialNumberSupplierFactory.create())
            .build()

        pinky.generate()
    }

    @Config(sdk = [25])
    @Test
    fun `generate() returns UUID based on pre-Oreo Serial and Board`() {
        val expectedUUID = "728deff9-84cb-3954-8d8f-9d11c80daf6c"
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "BOARD", BOARD)
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "SERIAL", SERIAL)
        val pinky = Pinky.Builder()
            .addIdSupplierFactory(SerialNumberSupplierFactory.create())
            .build()

        val uuid = pinky.generate()

        assertEquals(expectedUUID, uuid.toString())
    }

    @Config(sdk = [25])
    @Test
    fun `generate() returns UUID based on pre-Oreo phone id`() {
        val expectedUUID = "7de3eb16-5bc0-3cdc-8b42-2b4a8cd3749c"
        shadowTelephonyManager.setDeviceId(DEVICE_ID)
        val pinky = Pinky.Builder()
            .addIdSupplierFactory(PhoneIdSupplierFactory.create(context))
            .build()

        val uuid = pinky.generate()

        assertEquals(expectedUUID, uuid.toString())
    }

    @Config(sdk = [26])
    @Test
    fun `generate() returns UUID based on after Oreo IMEI`() {
        val expectedUUID = "b24bb70a-9a74-3d58-b962-66924afca025"
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_GSM)
        shadowTelephonyManager.setImei(IMEI)
        val pinky = Pinky.Builder()
            .addIdSupplierFactory(PhoneIdSupplierFactory.create(context))
            .build()

        val uuid = pinky.generate()

        assertEquals(expectedUUID, uuid.toString())
    }

    @Config(sdk = [26])
    @Test
    fun `generate() returns UUID based on after Oreo MEID`() {
        val expectedUUID = "61291c4f-3c2c-3a3d-86ed-386be050dbd6"
        shadowTelephonyManager.setPhoneType(TelephonyManager.PHONE_TYPE_CDMA)
        shadowTelephonyManager.setMeid(MEID)
        val pinky = Pinky.Builder()
            .addIdSupplierFactory(PhoneIdSupplierFactory.create(context))
            .build()

        val uuid = pinky.generate()

        assertEquals(expectedUUID, uuid.toString())
    }

    @Test
    fun `generate() returns UUID based on AndroidId`() {
        val expectedUUID = "35af1eef-b6d3-341b-b053-1a24d8c4eeaf"
        Settings.Secure.putString(contentResolver, Settings.Secure.ANDROID_ID, ANDROID_ID)
        val pinky = Pinky.Builder()
            .addIdSupplierFactory(AndroidIdSupplierFactory.create(context))
            .build()

        val uuid = pinky.generate()

        assertEquals(expectedUUID, uuid.toString())
    }
}