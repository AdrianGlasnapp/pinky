package io.glass.pinky.androidid

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import androidx.test.core.app.ApplicationProvider
import io.glass.pinky.Strings
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AndroidIdSupplierTest {
    private lateinit var contentResolver: ContentResolver
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        contentResolver = context.contentResolver
    }

    @Test
    fun provideShouldReturnAndroidIdIfItWasSet() {
        val androidIdProvider = AndroidIdSupplier(context)
        Settings.Secure.putString(contentResolver, Settings.Secure.ANDROID_ID, ANDROID_ID)

        val observed = androidIdProvider.provide()

        assertEquals(ANDROID_ID, observed)
    }

    @Test
    fun provideShouldReturnAndroidIdIfItWasNotSet() {
        val androidIdProvider = AndroidIdSupplier(context)

        val observed = androidIdProvider.provide()

        assertEquals(Strings.EMPTY, observed)
    }

    companion object {
        const val ANDROID_ID = "9774d56d682e549c"
    }
}