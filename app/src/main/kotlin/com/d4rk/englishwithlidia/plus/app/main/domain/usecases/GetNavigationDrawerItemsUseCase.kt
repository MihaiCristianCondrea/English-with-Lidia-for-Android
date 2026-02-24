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

package com.d4rk.englishwithlidia.plus.app.main.domain.usecases

import com.d4rk.android.libs.apptoolkit.app.main.domain.repository.NavigationRepository
import com.d4rk.android.libs.apptoolkit.core.domain.repository.FirebaseController
import com.d4rk.android.libs.apptoolkit.core.ui.model.navigation.NavigationDrawerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class GetNavigationDrawerItemsUseCase(private val navigationRepository: NavigationRepository, private val firebaseController: FirebaseController) {

    operator fun invoke(): Flow<List<NavigationDrawerItem>> {
        return navigationRepository.getNavigationDrawerItems().onStart {
            firebaseController.logBreadcrumb(
                message = "Navigation drawer load started",
                attributes = mapOf("source" to "GetNavigationDrawerItemsUseCase"),
            )
        }
    }
}
