package com.d4rk.englishwithlidia.plus.core.utils.extensions

import android.database.sqlite.SQLiteException
import com.d4rk.englishwithlidia.plus.core.domain.model.network.Errors
import kotlinx.serialization.SerializationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.SQLException

class ErrorsExtensionsTest {

    @Test
    fun `UnknownHostException maps to NO_INTERNET`() {
        val error = UnknownHostException()
        assertEquals(Errors.Network.NO_INTERNET, error.toError())
    }

    @Test
    fun `SocketTimeoutException maps to REQUEST_TIMEOUT`() {
        val error = SocketTimeoutException()
        assertEquals(Errors.Network.REQUEST_TIMEOUT, error.toError())
    }

    @Test
    fun `ConnectException maps to NO_INTERNET`() {
        val error = ConnectException()
        assertEquals(Errors.Network.NO_INTERNET, error.toError())
    }

    @Test
    fun `SerializationException maps to SERIALIZATION`() {
        val error = SerializationException()
        assertEquals(Errors.Network.SERIALIZATION, error.toError())
    }

    @Test
    fun `SQLException maps to DATABASE_OPERATION_FAILED`() {
        val error = SQLException()
        assertEquals(Errors.Database.DATABASE_OPERATION_FAILED, error.toError())
    }

    @Test
    fun `SQLiteException maps to DATABASE_OPERATION_FAILED`() {
        val error = SQLiteException()
        assertEquals(Errors.Database.DATABASE_OPERATION_FAILED, error.toError())
    }

    @Test
    fun `IllegalArgumentException maps to ILLEGAL_ARGUMENT`() {
        val error = IllegalArgumentException()
        assertEquals(Errors.UseCase.ILLEGAL_ARGUMENT, error.toError())
    }

    @Test
    fun `Unknown exception maps to NO_DATA`() {
        val error = RuntimeException()
        assertEquals(Errors.UseCase.NO_DATA, error.toError())
    }
}

