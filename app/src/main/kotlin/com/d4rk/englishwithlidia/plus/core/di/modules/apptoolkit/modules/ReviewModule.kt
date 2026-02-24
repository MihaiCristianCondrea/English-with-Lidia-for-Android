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

package com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules

import com.d4rk.android.libs.apptoolkit.app.review.data.repository.ReviewRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.review.domain.repository.ReviewRepository
import com.d4rk.android.libs.apptoolkit.app.review.domain.usecases.ForceInAppReviewUseCase
import com.d4rk.android.libs.apptoolkit.app.review.domain.usecases.RequestInAppReviewUseCase
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

val reviewModule: Module = module {
    single<ReviewRepository> { ReviewRepositoryImpl(dataStore = get()) }
    single<RequestInAppReviewUseCase> { RequestInAppReviewUseCase(reviewRepository = get()) }
    single<ForceInAppReviewUseCase> { ForceInAppReviewUseCase(reviewRepository = get()) }
}
