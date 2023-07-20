package ru.mangotest.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import ru.mangotest.BuildConfig
import ru.mangotest.core.ResponseHandler
import ru.mangotest.data.remote.AuthInterceptor
import ru.mangotest.data.remote.TokenRefreshmentService
import ru.mangotest.data.remote.api.AuthenticationApi
import ru.mangotest.data.remote.api.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideTokenService(
        loggingInterceptor: HttpLoggingInterceptor,
        responseHandler: ResponseHandler,
        converter: Converter.Factory
    ) = TokenRefreshmentService(
        handler = responseHandler,
        authenticationApi = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(converter)
            .build().create()
    )

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory =
        Json.Default.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(converter)
        .build()

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthenticationApi = retrofit.create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()
}