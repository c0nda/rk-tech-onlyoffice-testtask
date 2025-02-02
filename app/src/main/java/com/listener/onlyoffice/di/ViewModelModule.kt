package com.listener.onlyoffice.di

import androidx.lifecycle.ViewModel
import com.listener.onlyoffice.di.viewmodel.ViewModelKey
import com.listener.onlyoffice.presentation.auth.AuthViewModel
import com.listener.onlyoffice.presentation.documents.docs.DocsViewModel
import com.listener.onlyoffice.presentation.documents.rooms.RoomsViewModel
import com.listener.onlyoffice.presentation.documents.trash.TrashViewModel
import com.listener.onlyoffice.presentation.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(DocsViewModel::class)
    abstract fun bindDocsViewModel(viewModel: DocsViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(RoomsViewModel::class)
    abstract fun bindRoomsViewModel(viewModel: RoomsViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(TrashViewModel::class)
    abstract fun bindTrashViewModel(viewModel: TrashViewModel): ViewModel
}