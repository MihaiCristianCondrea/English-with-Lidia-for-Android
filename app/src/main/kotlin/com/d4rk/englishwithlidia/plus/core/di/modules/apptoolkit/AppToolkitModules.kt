package com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit

import com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules.appToolkitCoreModule
import com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules.helpModule
import com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules.issueReporterModule
import com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules.reviewModule
import com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules.supportModule
import org.koin.core.module.Module

val appToolkitModules: List<Module> = listOf(
    appToolkitCoreModule,
    supportModule,
    helpModule,
    issueReporterModule,
    reviewModule,
)
