package com.d4rk.englishwithlidia.plus.app.lessons.list.data


// TODO: Old unit tests
/*


@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryImplTest {

    @Test
    fun `getHomeLessons emits mapped HomeScreen when remote data source returns payload`() = runTest {
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val remoteDataSource = HomeRemoteDataSource {
            ApiHomeResponse(
                data = listOf(
                    ApiHomeLessons(
                        lessonId = "1",
                        lessonTitle = "Lesson 1",
                        lessonType = "video",
                        lessonThumbnailImageUrl = "url1",
                        lessonDeepLinkPath = "path1",
                    )
                )
            )
        }
        val repository = HomeRepositoryImpl(
            dispatchers = dispatchers,
            mapper = HomeMapper(),
            remoteDataSource = remoteDataSource,
        )

        val result = repository.getHomeLessons().first()

        val expected = HomeScreen(
            lessons = listOf(
                HomeLesson(
                    lessonId = "1",
                    lessonTitle = "Lesson 1",
                    lessonType = "video",
                    lessonThumbnailImageUrl = "url1",
                    lessonDeepLinkPath = "path1",
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `getHomeLessons emits empty HomeScreen when remote returns null`() = runTest {
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val remoteDataSource = HomeRemoteDataSource { null }
        val repository = HomeRepositoryImpl(
            dispatchers = dispatchers,
            mapper = HomeMapper(),
            remoteDataSource = remoteDataSource,
        )

        val result = repository.getHomeLessons().first()

        assertTrue(result.lessons.isEmpty())
    }

    @Test
    fun `getHomeLessons emits empty HomeScreen when remote throws exception`() = runTest {
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val remoteDataSource = HomeRemoteDataSource {
            throw IllegalStateException("boom")
        }
        val repository = HomeRepositoryImpl(
            dispatchers = dispatchers,
            mapper = HomeMapper(),
            remoteDataSource = remoteDataSource,
        )

        val result = repository.getHomeLessons().first()

        assertTrue(result.lessons.isEmpty())
    }

    @Test
    fun `getHomeLessons propagates cancellation`() = runTest {
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val remoteDataSource = HomeRemoteDataSource {
            throw CancellationException("cancelled")
        }
        val repository = HomeRepositoryImpl(
            dispatchers = dispatchers,
            mapper = HomeMapper(),
            remoteDataSource = remoteDataSource,
        )

        assertThrows<CancellationException> {
            repository.getHomeLessons().first()
        }
    }
}
*/
