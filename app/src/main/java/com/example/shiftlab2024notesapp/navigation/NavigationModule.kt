package com.example.shiftlab2024notesapp.navigation

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

val navModule = module {

    singleOf(::GlobalRouterImpl) binds arrayOf(GlobalRouter::class, NavControllerHolder::class)
}