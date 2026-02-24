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

plugins {
    alias(notation = libs.plugins.android.application) apply false
    alias(notation = libs.plugins.kotlin.compose) apply false
    alias(notation = libs.plugins.kotlin.serialization) apply false
    alias(notation = libs.plugins.google.mobile.services) apply false
    alias(notation = libs.plugins.firebase.crashlytics) apply false
    alias(notation = libs.plugins.firebase.performance) apply false
    alias(notation = libs.plugins.about.libraries) apply false
    alias(notation = libs.plugins.mannodermaus.android.junit5) apply false
}