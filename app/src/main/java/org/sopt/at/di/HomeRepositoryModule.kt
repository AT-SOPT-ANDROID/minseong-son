package org.sopt.at.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.at.repository.home.HomeDataRepository
import org.sopt.at.repository.home.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(): HomeRepository = HomeDataRepository()
}