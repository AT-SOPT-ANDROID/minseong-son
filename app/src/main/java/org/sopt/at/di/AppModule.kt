package org.sopt.at.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.at.core.network.AtSoptService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAtSoptService(retrofit: Retrofit): AtSoptService {
        return retrofit.create(AtSoptService::class.java)
    }
}