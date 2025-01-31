package com.listener.onlyoffice.di

import androidx.lifecycle.ViewModel
import com.listener.onlyoffice.di.viewmodel.ViewModelKey
import com.listener.onlyoffice.presentation.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}