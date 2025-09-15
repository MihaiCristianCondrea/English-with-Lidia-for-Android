package com.d4rk.englishwithlidia.plus.app.lessons.list.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiHomeResponse
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class HomeRepositoryImpl(
    private val client: HttpClient,
    private val dispatchers: DispatcherProvider,
    private val mapper: HomeMapper,
    baseRepositoryUrl: String,
) : HomeRepository {

    private val repositoryBaseUrl = ApiConstants.resolveRepositoryBaseUrl(baseRepositoryUrl)
    private val environmentSegment: String
        get() = if (BuildConfig.DEBUG) "debug" else "release"

    private val lessonsListUrl: String
        get() = listOf(
            repositoryBaseUrl,
            environmentSegment,
            ApiConstants.DEFAULT_LANGUAGE_CODE,
            "home",
            "api_get_lessons.json",
        ).joinToString(separator = "/")

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override fun getHomeLessons(): Flow<HomeScreen> =
        flow {
            val jsonString = client.get(lessonsListUrl).bodyAsText()
            val homeScreen = jsonString.takeUnless { it.isBlank() }
                ?.let { jsonParser.decodeFromString<ApiHomeResponse>(it) }
                ?.takeIf { it.data.isNotEmpty() }
                ?.let { mapper.map(it) }
                ?: HomeScreen()

            emit(homeScreen)
        }.catch { throwable ->
            if (throwable is CancellationException) throw throwable
            emit(HomeScreen())
        }.flowOn(dispatchers.io)
}
