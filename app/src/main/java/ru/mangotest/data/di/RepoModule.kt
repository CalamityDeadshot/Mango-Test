package ru.mangotest.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mangotest.data.local.AuthStateStorageImpl
import ru.mangotest.data.repository.AuthenticationRepositoryImpl
import ru.mangotest.domain.local.AuthStateStorage
import ru.mangotest.domain.repository.AuthenticationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindAuthStateStorage(
        concretion: AuthStateStorageImpl
    ): AuthStateStorage

    @Binds
    abstract fun bindAuthRepository(
        concretion: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}