package io.glass.pinky.serialnumber

import android.Manifest
import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.apache.xerces.util.XMLSymbols.EMPTY_STRING
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowApplication
import org.robolectric.shadows.ShadowBuild
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class OreoSerialNumberSupplierTest {
    private lateinit var shadowApplication: ShadowApplication
    private lateinit var serialNumberSupplier: OreoSerialNumberSupplier

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        shadowApplication = Shadows.shadowOf(context)
        shadowApplication.grantPermissions(Manifest.permission.READ_PHONE_STATE)
        serialNumberSupplier = OreoSerialNumberSupplier()
    }

    @Config(sdk = [26], shadows = [ShadowBuild::class])
    @Test
    fun provideReturnsEmptyStringWhenSerialIsNull() {
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "BOARD", null)
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "SERIAL", null)

        val observed = serialNumberSupplier.provide()

        assertEquals(EMPTY_STRING, observed)
    }

    @Config(sdk = [26], shadows = [ShadowBuild::class])
    @Test
    fun provideReturnsEmptyStringWhenSerialIsNullAndBoardIsNotNull() {
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "BOARD", BOARD)
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "SERIAL", null)

        val observed = serialNumberSupplier.provide()

        assertEquals(EMPTY_STRING, observed)
    }

    //TODO: find a way to mock Build.getSerial() and write test

    companion object {
        const val SERIAL = "B62AX"
        const val BOARD = "board"
    }
}