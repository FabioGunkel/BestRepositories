package com.example.usecase.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val usecaseModule = Kodein.Module("UseCaseModule"){
    bind()
}