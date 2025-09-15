package com.d4rk.englishwithlidia.plus.core.utils.extensions

import android.database.sqlite.SQLiteException
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.UiTextHelper
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.core.domain.model.network.Errors
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.SQLException

fun Errors.asUiText(): UiTextHelper {
    return when (this) {
        // Network errors
        Errors.Network.NO_INTERNET -> UiTextHelper.StringResource(R.string.error_no_internet)
        Errors.Network.REQUEST_TIMEOUT -> UiTextHelper.StringResource(R.string.error_request_timeout)
        Errors.Network.SERIALIZATION -> UiTextHelper.StringResource(R.string.error_serialization)

        // UseCase errors
        Errors.UseCase.NO_DATA -> UiTextHelper.StringResource(R.string.error_no_data)
        Errors.UseCase.FAILED_TO_LOAD_APPS -> UiTextHelper.StringResource(R.string.error_failed_to_load_apps)
        Errors.UseCase.ILLEGAL_ARGUMENT -> UiTextHelper.StringResource(R.string.error_illegal_argument)

        // Database errors
        Errors.Database.DATABASE_OPERATION_FAILED -> UiTextHelper.StringResource(R.string.error_database_operation_failed)

        else -> UiTextHelper.StringResource(R.string.unknown_error)
    }
}

fun Throwable.toError(default: Errors = Errors.UseCase.NO_DATA): Errors {
    return when (this) {
        is UnknownHostException -> Errors.Network.NO_INTERNET
        is SocketTimeoutException -> Errors.Network.REQUEST_TIMEOUT
        is ConnectException -> Errors.Network.NO_INTERNET
        is SerializationException -> Errors.Network.SERIALIZATION
        is SQLException, is SQLiteException -> Errors.Database.DATABASE_OPERATION_FAILED
        is IllegalArgumentException -> Errors.UseCase.ILLEGAL_ARGUMENT
        else -> default
    }
}