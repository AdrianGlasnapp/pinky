package io.glass.pinky.serialnumber

import org.apache.xerces.util.XMLSymbols.EMPTY_STRING
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowBuild
import org.robolectric.util.ReflectionHelpers

class SerialNumberSupplierTest {
    private lateinit var serialNumberSupplier: SerialNumberSupplier

    @Before
    @Throws(Exception::class)
    fun setUp() {
        serialNumberSupplier = SerialNumberSupplier()
    }

    @Config(sdk = [25], shadows = [ShadowBuild::class])
    @Test
    fun provideReturnsEmptyStringWhenSerialIsNull() {
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "BOARD", null)
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "SERIAL", null)

        val observed = serialNumberSupplier.provide()

        assertEquals(EMPTY_STRING, observed)
    }

    @Config(sdk = [25], shadows = [ShadowBuild::class])
    @Test
    fun provideReturnsSerialWhenSerialIsPresent() {
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "BOARD", BOARD)
        ReflectionHelpers.setStaticField(android.os.Build::class.java, "SERIAL", SERIAL)

        val observed = serialNumberSupplier.provide()

        assertEquals(BOARD + SERIAL, observed)
    }

    companion object {
        const val SERIAL = "B62AX"
        const val BOARD = "board"
    }
}