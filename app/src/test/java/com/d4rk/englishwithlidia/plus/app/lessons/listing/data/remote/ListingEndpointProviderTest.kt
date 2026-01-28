/*
package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ListingEndpointProviderTest {

    @Test
    fun `BuildConfigListingEnvironmentResolver returns debug for debug builds`() {
        val resolver = BuildConfigListingEnvironmentResolver(isDebugBuild = true)

        assertEquals("debug", resolver.environment())
    }

    @Test
    fun `BuildConfigListingEnvironmentResolver returns release for non debug builds`() {
        val resolver = BuildConfigListingEnvironmentResolver(isDebugBuild = false)

        assertEquals("release", resolver.environment())
    }

    @Test
    fun `DefaultListingEndpointProvider builds lessons url using provided environment`() {
        val baseUrl = "https://example.com"
        val resolver = ListingEnvironmentResolver { "beta" }
        val provider = DefaultListingEndpointProvider(
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
