package ru.mangotest.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mangotest.data.local.UserDataStorageImpl
import ru.mangotest.data.local.auth_state.AuthStateStorageImpl
import ru.mangotest.data.local.messages.MockMessagesDataSource
import ru.mangotest.data.repository.AuthenticationRepositoryImpl
import ru.mangotest.data.repository.MessagesRepositoryImpl
import ru.mangotest.data.repository.UserRepositoryImpl
import ru.mangotest.domain.local.AuthStateStorage
import ru.mangotest.domain.local.MessagesDataSource
import ru.mangotest.domain.local.UserDataStorage
import ru.mangotest.domain.repository.AuthenticationRepository
import ru.mangotest.domain.repository.MessagesRepository
import ru.mangotest.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindAuthStateStorage(
        concretion: AuthStateStorageImpl
    ): AuthStateStorage

    @Binds
    abstract fun bindUserDataStorage(
        concretion: UserDataStorageImpl
    ): UserDataStorage

    @Binds
    abstract fun bindAuthRepository(
        concretion: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    abstract fun bindMessagesDataSource(
        concretion: MockMessagesDataSource
    ): MessagesDataSource

    @Binds
    abstract fun bindMessagesRepository(
        concretion: MessagesRepositoryImpl
    ): MessagesRepository

    @Binds
    abstract fun bindUserDataRepository(
        concretion: UserRepositoryImpl
    ): UserRepository
}