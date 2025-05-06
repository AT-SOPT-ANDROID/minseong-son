package org.sopt.at.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.at.repository.home.HomeDataRepository
import org.sopt.at.repository.home.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {

    @Binds //간단히 인터페이스 구현체 주입이니 binds, retrofit2 같이 복잡해지면 provides
    @Singleton
    abstract fun bindHomeRepository(
        homeDataRepository: HomeDataRepository
    ): HomeRepository
}