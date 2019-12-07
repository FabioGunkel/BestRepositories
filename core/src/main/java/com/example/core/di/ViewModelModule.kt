package com.example.core.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind

val viewModelModule = Kodein.Module("ViewModelModule"){
    bind()
}