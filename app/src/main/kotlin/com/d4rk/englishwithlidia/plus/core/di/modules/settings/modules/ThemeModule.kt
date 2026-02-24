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

package com.d4rk.englishwithlidia.plus.core.di.modules.settings.modules

import com.d4rk.android.libs.apptoolkit.app.theme.ui.style.colors.ColorPalette
import com.d4rk.android.libs.apptoolkit.app.theme.ui.style.colors.special.skin.skinPalette
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val themeModule: Module = module {
    single<ColorPalette>(named("skinPalette")) { skinPalette }

    single<ColorPalette> { get(named("skinPalette")) }
}
