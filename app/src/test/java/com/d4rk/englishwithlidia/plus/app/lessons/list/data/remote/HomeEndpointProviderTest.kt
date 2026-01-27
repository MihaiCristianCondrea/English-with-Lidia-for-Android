/*
package com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HomeEndpointProviderTest {

    @Test
    fun `BuildConfigHomeEnvironmentResolver returns debug for debug builds`() {
        val resolver = BuildConfigHomeEnvironmentResolver(isDebugBuild = true)

        assertEquals("debug", resolver.environment())
    }

    @Test
    fun `BuildConfigHomeEnvironmentResolver returns release for non debug builds`() {
        val resolver = BuildConfigHomeEnvironmentResolver(isDebugBuild = false)

        assertEquals("release", resolver.environment())
    }

    @Test
    fun `DefaultHomeEndpointProvider builds lessons url using provided environment`() {
        val baseUrl = "https://example.com"
        val resolver = HomeEnvironmentResolver { "beta" }
        val provider = DefaultHomeEndpointProvider(
            baseRepositoryUrl = baseUrl,
            environmentResolver = resolver,
        )

        assertEquals(
            "https://example.com/beta/ro/home/api_get_lessons.json",
            provider.lessons(),
        )
    }
}
*/
