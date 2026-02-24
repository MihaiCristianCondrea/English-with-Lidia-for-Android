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

import com.d4rk.android.libs.apptoolkit.app.issuereporter.data.local.DeviceInfoLocalDataSource
import com.d4rk.android.libs.apptoolkit.app.issuereporter.data.remote.IssueReporterRemoteDataSource
import com.d4rk.android.libs.apptoolkit.app.issuereporter.data.repository.IssueReporterRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.issuereporter.domain.model.github.GithubTarget
import com.d4rk.android.libs.apptoolkit.app.issuereporter.domain.providers.DeviceInfoProvider
import com.d4rk.android.libs.apptoolkit.app.issuereporter.domain.repository.IssueReporterRepository
import com.d4rk.android.libs.apptoolkit.app.issuereporter.domain.usecases.SendIssueReportUseCase
import com.d4rk.android.libs.apptoolkit.app.issuereporter.ui.IssueReporterViewModel
import com.d4rk.android.libs.apptoolkit.core.di.GithubToken
import com.d4rk.android.libs.apptoolkit.core.utils.constants.github.GithubConstants
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.string.toToken
import com.d4rk.englishwithlidia.plus.BuildConfig
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private val githubTokenQualifier = qualifier<GithubToken>()

val issueReporterModule: Module = module {
    single<IssueReporterRemoteDataSource> { IssueReporterRemoteDataSource(client = get()) }
    single<DeviceInfoProvider> { DeviceInfoLocalDataSource(get(), get()) }
    single<IssueReporterRepository> { IssueReporterRepositoryImpl(get(), get(), get()) }
    single<SendIssueReportUseCase> { SendIssueReportUseCase(get(), get(), get()) }
    single<String>(qualifier = named(name = "github_repository")) { "English-with-Lidia-for-Android" }
    single<GithubTarget> { GithubTarget(username = GithubConstants.GITHUB_USER, repository = get(qualifier = named("github_repository"))) }
    single<String>(qualifier = named("github_changelog")) { GithubConstants.githubChangelog(get<String>(named("github_repository"))) }
    single<String>(githubTokenQualifier) { BuildConfig.GITHUB_TOKEN.toToken() }

    viewModel {
        IssueReporterViewModel(
            sendIssueReport = get(),
            githubTarget = get(),
            githubToken = get(githubTokenQualifier),
            deviceInfoProvider = get(),
            firebaseController = get(),
            dispatchers = get(),
        )
    }
}
