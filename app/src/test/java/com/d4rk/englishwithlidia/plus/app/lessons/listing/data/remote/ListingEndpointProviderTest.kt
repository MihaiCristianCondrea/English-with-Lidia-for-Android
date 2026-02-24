/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
