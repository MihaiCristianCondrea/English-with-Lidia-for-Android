package com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote

/**
 * Provides the url used to retrieve the home lessons payload.
 */
fun interface HomeEndpointProvider {
    fun lessons(): String
}

/**
 * Resolves which backend environment should be used when building endpoints.
 */
fun interface HomeEnvironmentResolver {
    fun environment(): String
}

/**
 * Selects the backend environment based on the current build type.
 */
class BuildConfigHomeEnvironmentResolver(
    private val isDebugBuild: Boolean,
) : HomeEnvironmentResolver {
    override fun environment(): String = if (isDebugBuild) "debug" else "release"
}

/**
 * Default implementation that creates the remote endpoint used for the home screen.
 */
class DefaultHomeEndpointProvider(
    private val baseRepositoryUrl: String,
    private val environmentResolver: HomeEnvironmentResolver,
) : HomeEndpointProvider {
    override fun lessons(): String {
        val environment = environmentResolver.environment()
        return "$baseRepositoryUrl/$environment/ro/home/api_get_lessons.json"
    }
}
