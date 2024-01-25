package com.u1tramarinet.mediasharehelper.di

import com.u1tramarinet.mediasharehelper.data.repository.ArtistRepository
import com.u1tramarinet.mediasharehelper.data.repository.ArtistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModules {
    @Binds
    @Singleton
    abstract fun bindsArtistRepository(impl: ArtistRepositoryImpl): ArtistRepository
}