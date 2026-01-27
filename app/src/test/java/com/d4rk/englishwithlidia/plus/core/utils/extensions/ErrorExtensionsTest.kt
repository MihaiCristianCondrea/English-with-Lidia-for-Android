/*
package com.d4rk.englishwithlidia.plus.core.utils.extensions

import android.database.sqlite.SQLiteException
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.UiTextHelper
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.serialization.SerializationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.SQLException
import java.util.stream.Stream

class ErrorExtensionsTest {

    @ParameterizedTest(name = "{0} maps to {1}")
    @MethodSource("knownErrorProvider")
    fun `known throwables map to expected Errors`(throwable: Throwable, expected: AppErrors) {
        assertEquals(expected, throwable.toError())
    }

    @Test
    fun `unknown throwable maps to provided default and yields unknown error string`() {
        val fallbackError = AppErrors.UseCase.FAILED_TO_LOAD_APPS
        val unknownThrowable = Throwable("mysterious failure")

        val mappedError = unknownThrowable.toError(default = fallbackError)

        assertEquals(fallbackError, mappedError)

        val uiText = mappedError.toErrorMessage()
        assertTrue(uiText is UiTextHelper.StringResource)
        assertEquals(UiTextHelper.StringResource(R.string.unknown_error), uiText)
    }

    companion object {
        @JvmStatic
        fun knownErrorProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(UnknownHostException("custom unknown host"), AppErrors.Network.NO_INTERNET),
            Arguments.of(SocketTimeoutException("socket timed out"), AppErrors.Network.REQUEST_TIMEOUT),
            Arguments.of(ConnectException("connection refused"), AppErrors.Network.NO_INTERNET),
            Arguments.of(SerializationException("serialization issue"), AppErrors.Network.SERIALIZATION),
            Arguments.of(SQLException("sql failure"), AppErrors.Database.DATABASE_OPERATION_FAILED),
            Arguments.of(SQLiteException("sqlite failure"), AppErrors.Database.DATABASE_OPERATION_FAILED),
            Arguments.of(IllegalArgumentException("illegal argument"), AppErrors.UseCase.ILLEGAL_ARGUMENT)
        )
    }
}
*/
